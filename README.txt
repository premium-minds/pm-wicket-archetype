Requisitos:
 - java 7
 - maven
 - bash
 - postgresql client

Usar:
 - mvn archetype:generate -DarchetypeGroupId=com.premiumminds -DarchetypeArtifactId=wicket-template-archetype
 - cd <nome-projecto>
 - sh share/scripts/apply_sql.sh
 - mvn jetty:run
 - Abrir o browser em "localhost:8080"
 
Para importar no eclipse:
  - instalar o plugin "Eclipse Jetty" (http://marketplace.eclipse.org/content/eclipse-jetty)
  - ir a File->Import->Existing Maven Projects

Testar para desenvolvimento:
 - "mvn integration-test"
 - ir a "target/test-classes/projects/basic/project/basic"
 - "mvn jetty:run"