#!/bin/bash

set -e
cd "$(dirname $0)/.."

java -cp lib/h2*.jar org.h2.tools.RunScript \
  -url jdbc:h2:~/woodys-mart \
  -script src/main/sql/*.sql

