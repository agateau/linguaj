#!/bin/sh
set -e

mkdir -p po

# Extract all strings from java code, store them in po/messages.pot
xgettext --from-code=utf-8 --keyword=tr --keyword=trn:1,2 -o po/messages.pot *.java

# Go through all existing .po files and update them so that they contain the
# latest strings
cd po
for po_file in *.po ; do
    msgmerge --update $po_file messages.pot
done
