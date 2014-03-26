#!/bin/sh
set -e

# Copyright 2014 Aurélien Gâteau <mail@agateau.com>
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

mkdir -p po

# Extract all strings from java code, store them in po/messages.pot
xgettext --from-code=utf-8 --keyword=tr --keyword=trn:1,2 -o po/messages.pot *.java

# Go through all existing .po files and update them so that they contain the
# latest strings
cd po
for po_file in *.po ; do
    msgmerge --update $po_file messages.pot
done
