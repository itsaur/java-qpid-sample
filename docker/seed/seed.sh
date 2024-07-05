#!/bin/bash

# Exit on error.
set -o errexit

# Exit on unset variable or parameter.
set -o nounset

# Exit on pipe failure.
set -o pipefail

: ${QPID_URL:=http://broker:5672}

function add-queue {
  local queue="${1}"
  if [[ $(qpid-stat --broker "${QPID_URL}" -q | grep "${queue}") ]]
  then
    echo "SKIPPED: ${queue}"
  else
    echo "CREATED: ${queue}"
    qpid-config --broker "${QPID_URL}" add queue "${queue}"
  fi
}

echo "Creating Queues"
for queue in $(echo ${QUEUES} | sed "s/,/ /g")
do
    add-queue ${queue}
done
echo ""
