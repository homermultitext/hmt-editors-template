#!/bin/bash

# Substitute name of directory as required in various config files 
# and scripts.
SED=`which sed`
DIR="$( cd "$( dirname "${BASH_SOURCE[0]}" )" && pwd )"

echo "Adjust paths to ${DIR}"

${SED} -i.bak 's#PATH/#${DIR}#g' vm-mom-config.gradle

cd scripts

${SED} -i.bak 's#PATH/#${DIR}#g' *.sh

