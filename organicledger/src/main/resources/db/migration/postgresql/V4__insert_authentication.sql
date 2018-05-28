INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
	VALUES ('web-agricalture-read', 'resource-server-rest-api',
	/*spring-security-oauth2-read-client-password1234*/'$2a$04$SAXk6M78Z1YfJfKT/ADAEefI.7vHjGPbyHQAtMkgZdCOclUzOBCg.',
	'read', 'password,authorization_code,refresh_token,implicit', 'USER', 1000, 5000);

INSERT INTO OAUTH_CLIENT_DETAILS(CLIENT_ID, RESOURCE_IDS, CLIENT_SECRET, SCOPE, AUTHORIZED_GRANT_TYPES, AUTHORITIES, ACCESS_TOKEN_VALIDITY, REFRESH_TOKEN_VALIDITY)
	VALUES ('web-agricalture-read-write', 'resource-server-rest-api',
	/*spring-security-oauth2-read-write-client-password1234*/'$2a$04$Sp5kgTMHcwpP6tEMrMHa7enuY5vpbNmytnpUUHWMvoB3FsK6ine62',
	'read,write', 'password,authorization_code,refresh_token,implicit', 'USER', 1000, 5000);
