#!/usr/bin/python

import os, sys

lib_path = os.path.abspath(os.path.join('..'))
sys.path.append(lib_path)

from json2html import *

# Get the source and target file
print 'Number of arguments:', len(sys.argv), 'arguments.'
print 'Argument List:', str(sys.argv)
sourceTempFile = sys.argv[1]
targetTempFile = sys.argv[2]

# Open and read the JSON source file
with open(sourceTempFile, 'r') as jsonF:
   jsondata = jsonF.read()
print "Read json is : ", jsondata

# Run conversion to HTML
jsonAsHTML = json2html.convert(json = jsondata)

# Write the resulting HTML representation of the JSON to target file
with open(targetTempFile, 'w+') as htmlF:
   htmlF.write(jsonAsHTML)

