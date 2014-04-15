CREATE TABLE schema_scripts_applied (
	script VARCHAR(200) NOT NULL,
	timestamp TIMESTAMP NOT NULL
);

CREATE TABLE schema_versions (
   version VARCHAR(20) NOT NULL PRIMARY KEY,
   timestamp TIMESTAMP
);
