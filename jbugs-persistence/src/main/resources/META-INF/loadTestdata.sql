-- User test data
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Dorel1', '1', '07414141', 'dorel1@a.com','doreld', '1234', 1);
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Dore2', '1', '07414141', 'dorel2@a.com','doreld1', '1234', 1 );
INSERT INTO USERS (firstname, lastname, phoneNumber, email, username, password, isActive) VALUES ('Dore3', '1', '07414141', 'dore3@a.com','ddorel', '1234', 1);

-- other tables TODO
INSERT INTO BUGS (description,fixedVersion,severity,status,targetDate,title,version,assignedTo,createdByUser)VALUES('blablabla','1.0.0','HIGH','NEW','2018-05-1','Bug1','2.0.0',1,2);

INSERT INTO PERMISSIONS (description,type) values ('User management permission', 'USER_MANAGEMENT');
INSERT INTO PERMISSIONS (description,type) values ('Bug management permission', 'BUG_MANAGEMENT');

INSERT INTO ROLES (type) values ('ADM');
INSERT INTO ROLES (type) values ('PM');
INSERT INTO ROLES (type) values ('TM');
INSERT INTO ROLES (type) values ('DEV');
INSERT INTO ROLES (type) values ('TEST');

