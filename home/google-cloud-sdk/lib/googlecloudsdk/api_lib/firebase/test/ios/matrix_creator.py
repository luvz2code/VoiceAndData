# -*- coding: utf-8 -*- #
# Copyright 2018 Google Inc. All Rights Reserved.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#    http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

"""Create iOS test matrices in Firebase Test Lab."""

from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals

import os
import uuid

from apitools.base.py import exceptions as apitools_exceptions

from googlecloudsdk.api_lib.firebase.test import matrix_ops
from googlecloudsdk.api_lib.firebase.test import util
from googlecloudsdk.calliope import exceptions
from googlecloudsdk.core import config
from googlecloudsdk.core import log


def CreateMatrix(args, context, history_id, gcs_results_root, release_track):
  """Creates a new iOS matrix test in Firebase Test Lab from the user's params.

  Args:
    args: an argparse namespace. All the arguments that were provided to this
      gcloud command invocation (i.e. group and command arguments combined).
    context: {str:obj} dict containing the gcloud command context, which
      includes the Testing API client+messages libs generated by Apitools.
    history_id: {str} A history ID to publish Tool Results to.
    gcs_results_root: the root dir for a matrix within the GCS results bucket.
    release_track: the release track that the command is invoked from.

  Returns:
    A TestMatrix object created from the supplied matrix configuration values.
  """
  creator = MatrixCreator(args, context, history_id, gcs_results_root,
                          release_track)
  return creator.CreateTestMatrix(uuid.uuid4().hex)


class MatrixCreator(object):
  """Creates a single iOS test matrix based on user-supplied test arguments."""

  def __init__(self, args, context, history_id, gcs_results_root,
               release_track):
    """Construct an MatrixCreator to be used to create a single test matrix.

    Args:
      args: an argparse namespace. All the arguments that were provided to this
        gcloud command invocation (i.e. group and command arguments combined).
      context: {str:obj} dict containing the gcloud command context, which
        includes the Testing API client+messages libs generated by Apitools.
      history_id: {str} A history ID to publish Tool Results to.
      gcs_results_root: the root dir for a matrix within the GCS results bucket.
      release_track: the release track that the command is invoked from.
    """
    self._project = util.GetProject()
    self._args = args
    self._history_id = history_id
    self._gcs_results_root = gcs_results_root
    self._client = context['testing_client']
    self._messages = context['testing_messages']
    self._release_track = release_track

  def _BuildFileReference(self, filename):
    """Build a FileReference pointing to a file in GCS."""
    if not filename:
      return None
    path = os.path.join(self._gcs_results_root, os.path.basename(filename))
    return self._messages.FileReference(gcsPath=path)

  def _BuildIosXcTestSpec(self):
    """Build a TestSpecification for an IosXcTest."""
    setup = self._messages.IosTestSetup(
        networkProfile=getattr(self._args, 'network_profile', None))
    spec = self._messages.TestSpecification(
        disableVideoRecording=not self._args.record_video,
        iosTestSetup=setup,
        testTimeout=matrix_ops.ReformatDuration(self._args.timeout),
        iosXcTest=self._messages.IosXcTest(
            testsZip=self._BuildFileReference(self._args.test),
            xctestrun=self._BuildFileReference(self._args.xctestrun_file)))
    return spec

  def _TestSpecFromType(self, test_type):
    """Map a test type into its corresponding TestSpecification message ."""
    if test_type == 'xctest':
      return self._BuildIosXcTestSpec()
    else:  # It's a bug in our arg validation if we ever get here.
      raise exceptions.InvalidArgumentException(
          'type', 'Unknown test type "{}".'.format(test_type))

  def _BuildTestMatrix(self, spec):
    """Build just the user-specified parts of an iOS TestMatrix message.

    Args:
      spec: a TestSpecification message corresponding to the test type.

    Returns:
      A TestMatrix message.
    """
    devices = [self._BuildIosDevice(d) for d in self._args.device]
    environment_matrix = self._messages.EnvironmentMatrix(
        iosDeviceList=self._messages.IosDeviceList(iosDevices=devices))

    gcs = self._messages.GoogleCloudStorage(gcsPath=self._gcs_results_root)
    hist = self._messages.ToolResultsHistory(projectId=self._project,
                                             historyId=self._history_id)
    results = self._messages.ResultStorage(googleCloudStorage=gcs,
                                           toolResultsHistory=hist)

    client_info = self._messages.ClientInfo(
        name='gcloud',
        clientInfoDetails=[
            self._messages.ClientInfoDetail(
                key='Cloud SDK Version', value=config.CLOUD_SDK_VERSION),
            self._messages.ClientInfoDetail(
                key='Release Track', value=self._release_track)
        ])

    return self._messages.TestMatrix(
        testSpecification=spec,
        environmentMatrix=environment_matrix,
        clientInfo=client_info,
        resultStorage=results)

  def _BuildIosDevice(self, device_map):
    return self._messages.IosDevice(
        iosModelId=device_map['model'],
        iosVersionId=device_map['version'],
        locale='en_US',  # TODO(b/78015882): add real locale/orientation support
        orientation='portrait')

  def _BuildTestMatrixRequest(self, request_id):
    """Build a TestingProjectsTestMatricesCreateRequest for a test matrix.

    Args:
      request_id: {str} a unique ID for the CreateTestMatrixRequest.

    Returns:
      A TestingProjectsTestMatricesCreateRequest message.
    """
    spec = self._TestSpecFromType(self._args.type)
    return self._messages.TestingProjectsTestMatricesCreateRequest(
        projectId=self._project,
        testMatrix=self._BuildTestMatrix(spec),
        requestId=request_id)

  def CreateTestMatrix(self, request_id):
    """Invoke the Testing service to create a test matrix from the user's args.

    Args:
      request_id: {str} a unique ID for the CreateTestMatrixRequest.

    Returns:
      The TestMatrix response message from the TestMatrices.Create rpc.

    Raises:
      HttpException if the test service reports an HttpError.
    """
    request = self._BuildTestMatrixRequest(request_id)
    log.debug('TestMatrices.Create request:\n{0}\n'.format(request))
    try:
      response = self._client.projects_testMatrices.Create(request)
      log.debug('TestMatrices.Create response:\n{0}\n'.format(response))
    except apitools_exceptions.HttpError as error:
      msg = 'Http error while creating test matrix: ' + util.GetError(error)
      raise exceptions.HttpException(msg)

    log.status.Print('Test [{id}] has been created in the Google Cloud.'
                     .format(id=response.testMatrixId))
    return response
