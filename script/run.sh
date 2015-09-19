#!/bin/bash

set -e
cd "$(dirname $0)/.."

mvn package -Dtest.skip=true
java -cp target/classes:"target/dependency/*" it.xpug.todolists.main.Main
