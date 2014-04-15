Usar:
 - mvn archetype:generate -DarchetypeGroupId=com.premiumminds -DarchetypeArtifactId=wicket-template-archetype
 - cd <nome-projecto>
 - sh share/scripts/apply_sql.sh
 - mvn jetty:run
 Abrir o browser em "localhost:8080"

Testar:
 - "mvn integration-test"
 - ir a "target/test-classes/projects/basic/project/basic"
 - "mvn jetty:run"