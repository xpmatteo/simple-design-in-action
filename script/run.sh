#!/bin/bash

set -e
cd "$(dirname $0)/.."

mkdir -p target/classes 2> /dev/null || true
javac -g -d target/classes -cp 'lib/*' -sourcepath src/main/java $(find src/main/java -iname *.java)
java -cp 'target/classes:lib/*' it.xpug.woodysmart.main.Main
