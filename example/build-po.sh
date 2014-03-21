#!/bin/sh
set -e
JAVAC=${JAVAC:-javac}

# Turn .po files from po/ into .java files in messages/

echo "Compiling languages"
mkdir -p messages
for po in po/*.po ; do
    echo "- $po"
    locale=$(basename $po .po)
    ../scripts/po-compile -o messages/Messages_$locale.java -l $locale $po
done
