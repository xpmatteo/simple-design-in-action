#!/bin/bash

set -e
cd "$(dirname $0)/.."

mvn package
java -cp target/classes:"target/dependency/*" it.xpug.lezione6.main.Main
