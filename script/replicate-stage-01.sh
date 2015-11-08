#!/bin/bash

set -e
cd $(dirname $0)/..

if [ $# -ne 1 ]; then
  echo "Usage: $0 <filename>"
  exit 1
fi

filename=$1

for stage in stage-0[2-9]*; do
 cp -r stage-01-hello-world/$filename $stage/$dirname
done

