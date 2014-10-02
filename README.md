pm-wicket-archetype
===================

Template to create a web application with Wicket + Guice + Hibernate

## Requirements

* Java 7
* Maven
* PostgreSQL server 9.x
* Bash (optional)
* PostgreSQL client (optional)

## Running your project in less than 5 minutes

### Create project

Run the following in your command line:
```bash
mvn archetype:generate -DarchetypeGroupId=com.premiumminds -DarchetypeArtifactId=pm-wicket-archetype
```
Next you will be asked to fill the following properties:
* `groupId` - the group id for your application
* `artifactId` - your application artifact name
* `version` - your initial version (defaults to *1.0-SNAPSHOT*)
* `package` - java package for the project (usually `groupId`.`artifactId`)
* `applicationTitle` - this is the full name of your project, will appear at your application homepage
* `databaseHost` - the host of your DEV database, usually *localhost* (port defaults to *5432*)
* `databaseName` - database name for your DEV environment (doesn't need to already exist)
* `databasePassword` - password to access your database
* `databaseUsername` - username to access your database
* `debugEmail` - all emails will be sent to this address in DEV

Then confirm all your properties and your project is complete :)

### Create database schema

To create the database schema (and database) run the following command:
```bash
cd <project dir>
sh share/scripts/apply_sql.sh
```
If your database doesn't exist you will be asked if the script can create it. Next all SQL scripts are executed and the application schema is created.

### Run it

...now the moment you are waiting for, just type:
```bash
mvn jetty:run
```
And open your favorite browser at [http://localhost:8080](http://localhost:8080).

The default user is `template@premium-minds.com` with password `test`

##Licence
Copyright (C) 2014 [Premium Minds](http://www.premium-minds.com/)

Licensed under the [GNU Lesser General Public Licence](http://www.gnu.org/licenses/lgpl.html)
