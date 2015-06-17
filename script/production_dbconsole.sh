#!/bin/bash
#
# Purpose: to work on the production database on Heroku
#
# To work interactively, just type
#    script/production_dbconsole.sh
#
# To send a file to the production database, use something like
#
#    cat MYFILE.sql | script/production_dbconsole.sh
#
# Prerequisites: you will need to install the Heroku tool

# stop at the first error
set -e

# change directory to the root of this project
cd "$(dirname "$0")/.."

# see if the heroku command is installed
hash heroku || {
  echo "Please install the 'heroku' tool.  See heroku.com for instructions"
  exit 1
}

# obtain the credentials
dburl=$(heroku pg:credentials DATABASE | grep ://)

# connect to postgres on heroku
psql $* $dburl

