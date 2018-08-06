# -*- coding: utf-8 -*- #
# Copyright 2014 Google Inc. All Rights Reserved.
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
"""Command for deleting HTTP health checks."""
from __future__ import absolute_import
from __future__ import division
from __future__ import unicode_literals

from googlecloudsdk.api_lib.compute import base_classes
from googlecloudsdk.api_lib.compute import utils
from googlecloudsdk.calliope import base
from googlecloudsdk.command_lib.compute import completers
from googlecloudsdk.command_lib.compute import flags as compute_flags
from googlecloudsdk.command_lib.compute.http_health_checks import flags


class Delete(base.DeleteCommand):
  """Delete HTTP health checks.

  *{command}* deletes one or more Google Compute Engine
  HTTP health checks.
  """

  HTTP_HEALTH_CHECK_ARG = None

  @staticmethod
  def Args(parser):
    Delete.HTTP_HEALTH_CHECK_ARG = flags.HttpHealthCheckArgument(plural=True)
    Delete.HTTP_HEALTH_CHECK_ARG.AddArgument(parser, operation_type='delete')
    parser.display_info.AddCacheUpdater(completers.HealthChecksCompleter)

  def Run(self, args):
    """Issues requests necessary to delete HTTP Health Checks."""
    holder = base_classes.ComputeApiHolder(self.ReleaseTrack())
    client = holder.client

    http_health_check_refs = Delete.HTTP_HEALTH_CHECK_ARG.ResolveAsResource(
        args,
        holder.resources,
        scope_lister=compute_flags.GetDefaultScopeLister(client))

    utils.PromptForDeletion(http_health_check_refs)

    requests = []
    for http_health_check_ref in http_health_check_refs:
      requests.append((client.apitools_client.httpHealthChecks, 'Delete',
                       client.messages.ComputeHttpHealthChecksDeleteRequest(
                           **http_health_check_ref.AsDict())))

    return client.MakeRequests(requests)
