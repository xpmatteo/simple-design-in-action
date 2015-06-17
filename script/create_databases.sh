#!/bin/bash
#
# Purpose: create all needed databases for the application,
# loading the schema and the test data

# define key information
project=todolists
dbpassword="secret"

# no customization needed beyond this line
db_development=${project}_development
db_test=${project}_test
dbuser=$project

# Stop at the first error
set -e

# Go to the main project directory
cd "$(dirname $0)/.."

# if we're on Linux
if uname -a | grep -qi linux; then
  # if the postgres user for the current login does not exist
  if ! psql -tAc "select 3 + 4" template1 > /dev/null 2> /dev/null; then
    # then create the postgres user with superuser privileges
    sudo -u postgres createuser --superuser $(whoami)
  fi
fi

# create user
createuser --no-superuser --createdb --no-createrole $dbuser || true

# create databases
for db in $db_development $db_test; do
	echo doing $db

	dropdb --if-exists $db
	createdb $db
	psql -tAc "ALTER USER $dbuser WITH PASSWORD '$dbpassword'" $db

	# load all sql scripts in database
	cat src/main/sql/???_*.sql src/main/sql/seed.sql | psql $db

	# grant all privileges on all tables to our user
	sql="select relname from pg_stat_user_tables union select relname from pg_statio_user_sequences"
	for table in $(psql -tAc "$sql" $db); do
	  psql -tAc "GRANT ALL PRIVILEGES ON TABLE $table TO $dbuser " $db
	done
done

echo "OK"
