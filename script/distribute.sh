#!/bin/bash

set -e

cd "$(dirname $0)/../.."

ORIG="simple-design-in-action"
DEST="/tmp/$ORIG.zip"
rm $DEST 2> /dev/null || true

zip -r $DEST $ORIG -x "*/\.DS_Store" -x "*/bin" -x "*/target" -x"*/\.git\*"
echo
echo "See your distribution file here:"
ls -lh $DEST


