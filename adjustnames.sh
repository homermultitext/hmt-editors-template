#!/bin/bash

# Substitute name of directory as required in various config files 
# and scripts.
SED=`which sed`
GIT=`which git`
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo USING $DIR for directory

echo "Adjust paths to ${DIR}"



${SED} -i.bak "s#PATH/#$DIR/#g" configs/vm-mom-config.gradle

cd scripts

${SED} -i.bak "s#PATH/#$DIR/#g" *.sh

cd ..


git add collections/*.csv
git add collections/*.xml
git add collections/schemas/*.rng
git add configs/cdmgr.conf
git add configs/vm-mom-config.gradle
git add converted/*css
git add converted/README.md
git add editions/*xml
git add editions/Iliad/*xml
git add editions/schemas/*rng
git add editions/scholia/*xml
git add indices/*csv
git add scripts/*.sh
git add writing/README.md



