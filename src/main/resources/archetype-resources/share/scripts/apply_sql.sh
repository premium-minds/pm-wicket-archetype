#!/bin/bash

set -e

. "$(cd "$(cd "$(dirname "$0")"; pwd -P)"/../../etc; pwd)"/database.properties

SCRIPTS_DIR="$(cd "$(cd "$(dirname "$0")"; pwd -P)"/../../src/main/sql; pwd)"

export PGOPTIONS='--client-min-messages=warning'
export PGPASSWORD=$PASSWORD

SCRIPTS_APPLIED=0

function apply_script {
	echo "Applying script $2"
	psql -X -1 -q --set ON_ERROR_STOP=on -h $HOST $DATABASE $USER < $1 > /dev/null
	echo "INSERT INTO schema_scripts_applied (script, timestamp) VALUES ('$2', CURRENT_TIMESTAMP);\q" | psql -q --set ON_ERROR_STOP=on -t -h $HOST $DATABASE $USER
	SCRIPTS_APPLIED=$[$SCRIPTS_APPLIED +1]
}

function create_database {
	echo "Creating database $DATABASE"
	createdb -h $HOST -U $USER $DATABASE
}

echo "Checking if database $DATABASE exists"
if [ "0" == `psql -h $HOST -U $USER -l | grep $DATABASE | wc -l` ]; then
	while true; do
		read -p "Database $DATABASE does not exist, create it? [y/n] " yn
		case $yn in
			[Yy]* ) create_database; break;;
			[Nn]* ) exit;;
			* ) echo "Please answer yes or no.";;
		esac
	done
fi

echo "Connecting to $HOST:$DATABASE with username: $USER"

HASTABLE=`echo "SELECT EXISTS(
    SELECT * 
    FROM information_schema.tables 
    WHERE 
      table_schema = 'public' AND 
      table_name = 'schema_scripts_applied'
);\q" | psql -t -h $HOST $DATABASE $USER`


for f in `find $SCRIPTS_DIR -name '*.sql' | sort`
do
	filename=`basename $f`
	if [ "t" == $HASTABLE ]; then
		# has table, check if script applied
		SCRIPT_APPLIED=`echo "SELECT COUNT(*)<>0 FROM schema_scripts_applied WHERE script='$filename';\q" | psql --set ON_ERROR_STOP=on -t -h $HOST $DATABASE $USER`
		if [ "f" == $SCRIPT_APPLIED ]; then
			apply_script $f $filename
		fi
	else
		apply_script $f $filename
	fi
done
echo "Applied $SCRIPTS_APPLIED scripts"


