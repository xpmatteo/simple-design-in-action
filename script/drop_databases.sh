#!/bin/bash
#
# Purpose: undo everything that create_databases.sh does

dropdb myproject_development
dropdb myproject_test
dropuser myproject

