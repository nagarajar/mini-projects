
`country_master`

INSERT INTO country_master VALUES (1, 'India');
INSERT INTO country_master VALUES (2, 'United States');

SELECT * FROM country_master;

=========================================================

`state_master`

INSERT INTO state_master VALUES (1,1, 'Karnataka');
INSERT INTO state_master VALUES (2,1, 'Andhra Pradesh');
INSERT INTO state_master VALUES (3,2, 'California');
INSERT INTO state_master VALUES (4,2, 'New Jersey');

SELECT * FROM state_master;

========================================================
`city_master`

INSERT INTO city_master VALUES (1,'Bangalore',1);
INSERT INTO city_master VALUES (2,'Mysore',1);
INSERT INTO city_master VALUES (3,'Anantapur',2);
INSERT INTO city_master VALUES (4,'Visakhapatnam',2);
INSERT INTO city_master VALUES (5,'Los Angeles',3);
INSERT INTO city_master VALUES (6,'San Diego',3);
INSERT INTO city_master VALUES (7,'Newark',4);
INSERT INTO city_master VALUES (8,'Jersey City',4);


SELECT * FROM city_master;