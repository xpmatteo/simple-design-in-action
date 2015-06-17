#!/bin/bash
#
# Purpose: create all needed databases for the application, in production
# loading the schema and the test data
#
# WARNING: this will destroy any existing database and production data!!!

# Stop at the first error
set -e

# Go to the main project directory
cd "$(dirname $0)/.."

# detect the url
url=$(heroku config | grep DATABASE_URL | awk '{ print $2 }')

# drop all tables
for table in $(psql -tAc "select relname from pg_stat_user_tables" $url); do
  psql -tAc "DROP TABLE $table " $url
done

# load all sql scripts in database
cat src/main/sql/???_*.sql src/main/sql/seed.sql | psql $url

echo "OK"
