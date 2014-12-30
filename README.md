pm-wicket-archetype
===================

Web application template with support for Wicket + Guice + Hibernate

## Requirements

* Java 7
* Maven
* PostgreSQL server 9.x
* Bash (optional)
* PostgreSQL client (optional)

## Run your project in less than 5 minutes

### Setup project

Open your terminal and run the following command:
```bash
mvn archetype:generate -DarchetypeGroupId=com.premiumminds -DarchetypeArtifactId=pm-wicket-archetype
```
Next, you will be asked to fill in the following properties:
* `groupId` - the group id for your application artifact
* `artifactId` - the artifact name of the application
* `version` - your initial version (defaults to *1.0-SNAPSHOT*)
* `package` - the project's base java package (usually `groupId`.`artifactId`)
* `applicationTitle` - the full name of your project. It will show up in your application's homepage
* `databaseHost` - the development database host. Usually *localhost* (port defaults to *5432*)
* `databaseName` - the development database name (doesn't need to already exist)
* `databasePassword` - The database password
* `databaseUsername` - The database username
* `debugEmail` - all emails will be sent to this address, while in DEV environment

Now validate that all your properties are correct and confirm. Your project is now set up :)

### Create database schema

Just run the following command to create the database and corresponding schema:
```bash
cd <project dir>
bash share/scripts/apply_sql.sh
```
If the database doesn't exist, you will be prompted for permission to create it. Following this, all SQL scripts are executed, setting up your database automatically.

### Run it

...now for the moment you've been waiting for, just type in:
```bash
mvn jetty:run
```
Open your favorite browser at [http://localhost:8080](http://localhost:8080). You will be presented with your application's login screen.

The default user is `template@premium-minds.com` with password `test`

##License
Copyright (C) 2014 [Premium Minds](http://www.premium-minds.com/)

Licensed under the [GNU Lesser General Public Licence](http://www.gnu.org/licenses/lgpl.html)
