Install application on Tomcat, for the first time:
 - Change POM version
 - mvn clean package
 - Copy the files from share/deployment/configs/* to your server
 - Modify those files that you copied
 - Assuming your tomcat is at /var/lib/tomcat7, copy the file share/deployment/tomcat/${artifactId}.xml
   to your server at $TOMCAT_DIR/conf/Catalina/localhost and edit so it points to your new configurations
 - Copy the ${artifactId}.war to your server
 - Copy the file at share/postgresql.jdbc4.jar to your server $TOMCAT_DIR/common
 
To update your application after installed, just overwrite your ${artifactId}.war

NOTE:
 - If you don't want to get surprises when Tomcat deletes the file ${artifactId}.xml, goto $TOMCAT_DIR/server.xml
   and change the Host->autoDeploy to "false"
