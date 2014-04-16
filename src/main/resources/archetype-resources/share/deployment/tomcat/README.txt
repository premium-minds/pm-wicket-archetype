Para instalar a aplicação num Tomcat, pela primeira vez:
 - Actualizar a versão do POM
 - mvn clean package
 - Copiar os ficheiros share/deployment/configs/* para o sitio onde se quer as configurações
 - Editar cada um destes ficheiros e por as configurações desejadas para produção
 - Assumindo que o tomcat está instalado na pastar /var/lib/tomcat7, copiar o 
   ficheiro share/deployment/tomcat/${artifactId}.xml para o servidor $TOMCAT_DIR/conf/Catalina/localhost
   e edita-lo para alterar a localização das configurações
 - Copiar o ${artifactId}.war para o sitio onde se quer por o war
 - Copiar o ficheiro share/postgresql.jdbc4.jar para o servidor $TOMCAT_DIR/common

Para actualizar a aplicação, basta substituir o ${artifactId}.war

NOTAS:
 - Para não ter surpresas do ficheiro ${artifactId}.xml ser apagado pelo tomcat, deve-se ir ao 
   ficheiro $TOMCAT_DIR/server.xml e alterar no Host o autoDeploy para autoDeploy=false