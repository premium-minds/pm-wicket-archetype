pm-wicket-archetype
===================

Web application template with support for Wicket + Guice + Hibernate

## Requirements

* Java 8
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
* `SMTPFrom` - your SMTP from address (used when you are in debug only)
* `SMTPPassword` - your SMTP account password (used when you are in debug only)
* `SMTPServer` - your SMTP host address (used when you are in debug only)
* `SMTPUsername` - your SMTP account username (used when you are in debug only)
* `applicationTitle` - the full name of your project. It will show up in your application's homepage
* `databaseHost` - the development database host. Usually *localhost* (port defaults to *5432*)
* `databaseName` - the development database name (doesn't need to already exist)
* `databasePassword` - The database password
* `databaseUsername` - The database username
* `debugEmail` - all emails will be sent to this address, while in DEV environment

Now validate that all your properties are correct and confirm. Your project is now set up :)

### Create database schema

Create the database manually. The archetype includes Flyway and will create the schema automatically when it runs.

### Run it

...now for the moment you've been waiting for, just type in:
```bash
mvn jetty:run
```
Open your favorite browser at [http://localhost:8080](http://localhost:8080). You will be presented with your application's login screen.

The default user is `template@premium-minds.com` with password `test`

##Continuous Integration
[![Build Status](https://travis-ci.org/premium-minds/pm-wicket-archetype.png?branch=master)](https://travis-ci.org/premium-minds/pm-wicket-archetype)

CI is hosted by [travis-ci.org](https://travis-ci.org/)

##License
Copyright (C) 2014 [Premium Minds](http://www.premium-minds.com/)

Licensed under the [GNU Lesser General Public Licence](http://www.gnu.org/licenses/lgpl.html)
