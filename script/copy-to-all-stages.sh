#!/bin/bash

set -e
cd $(dirname $0)/..

if [ $# -ne 2 ]; then
  echo "Usage: $0 <filename> <target-directory>"
  exit 1
fi

filename=$1
dirname=$2

for stage in stage-*; do
 cp $filename $stage/$dirname
done

rm $filename

