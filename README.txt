Usar:
 - mvn archetype:generate -DarchetypeGroupId=com.premiumminds -DarchetypeArtifactId=wicket-template-archetype


Testar:
 - "mvn integration-test"
 - ir a "target/test-classes/projects/basic/project/basic"
 - "mvn jetty:run"