#!/usr/bin/python

import speech_recognition as sr
import sys

if len(sys.argv) < 2:
	print('To few arguments, please specify a filename')
	quit()

filename = sys.argv[1]

r = sr.Recognizer()

file = sr.AudioFile(filename)

with file as source:
	audio = r.record(source)

final_text = r.recognize_google(audio)
print(final_text)

text = r.recognize_google(audio, show_all=True)
print(text)

