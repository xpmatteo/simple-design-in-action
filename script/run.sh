#!/bin/bash

set -e
cd "$(dirname $0)/.."

mvn package -DskipTests=true
java -cp target/classes:"target/dependency/*" it.xpug.woodysmart.main.Main
