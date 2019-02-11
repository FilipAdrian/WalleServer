CREATE SEQUENCE public.country_id_seq
START WITH 61001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.country
(
   id integer primary key DEFAULT nextval('public.country_id_seq'::regclass),
   name varchar (50) UNIQUE NOT NULL
);


INSERT INTO country (name)
VALUES
('USA'),
('GERMANY'),
('SOUTH KOREA'),
('ITALY'),
('CZECH REPUBLIC'),
('RUSSIA'),
('FRANCE'),
('JAPAN'),
('ESTONIA'),
('MOLDOVA');

CREATE SEQUENCE public.manufacture_id_seq
START WITH 11001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.manufacture
(
    id integer primary key DEFAULT nextval('public.manufacture_id_seq'::regclass),
    name varchar (255) UNIQUE NOT NULL,
    id_country integer NOT NULL,
    address varchar(255) NOT NULL,
	FOREIGN KEY (id_country) REFERENCES country (id)
);

INSERT INTO manufacture (name,id_country, address)
VALUES
('CAN-AM',61001,'Wyoming, 44th Street SW, 720'),
('MERCEDES-BENZ',61002,'Stuttgart, Stuttgarter Strasse, 90'),
('ATE',61002,'Frankfurt am Main, Guerickestrasse,7'),
('ONNURI',61003,'Hanam-si, Misa-daero, 612'),
('SCT',61002,'Wedel, Feldstrasse, 154'),
('ABE',61002,'Berlin, Josef-Orlopp-Strasse, 55'),
('DELPHI',61004,'Cinisello Balsamo, Via Lavoratori ,124'),
('TOYOTA',61005,'Zajeci, Skolni, 401'),
('AMD',61006,'Ufa, Dmitrii Donskii, 65'),
('Gates',61001,'Denver, 15th Street, 1144'),
('Renault',61007,'Boulogne-Billancourt, Quai Alphonse le Gallo, 13'),
('BREMBO',61004,'Lallio, Stezzano, 2'),
('OS-RAM',61002,'München, Marcel-Breuer-Strasse, 6'),
('KIA/Hyundai',61002,'Frankfurt am Main, Wilhelm-Fay-Strasse, 51'),
('MANDO',61003,'Bundang-gu, Pangyo-ro 255 beon-gil, 21'),
('Mazda',61008,'NISHIKI NAKA-KU NAGOYA, Sakae VT, 3'),
('FEBEST',61009,'Narva, Kadastiku, 23');

CREATE SEQUENCE public.warehouse_id_seq
START WITH 71001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.warehouse
(
	id integer primary key DEFAULT nextval('public.warehouse_id_seq'::regclass),
	name varchar (50) NOT NULL,
	id_country integer NOT NULL,
	address varchar (100) NOT NULL,
	phone varchar (50) NOT NULL,
	FOREIGN KEY (id_country) REFERENCES country (id)
);

INSERT INTO warehouse (name,id_country,address,phone)
VALUES
('Depozit 1',61010,'Mun. Chisinau, Bd. Dacia 135/3','+37379123456'),
('Depozit 2',61010,'Mun. Chisinau, Bd. Alecu Russo 54','+37398765431'),
('Depozit 3',61010,'Mun. Chisinau, Str. Studentilor 7','+37312345678');


CREATE TABLE public.product
(
    id varchar (50) PRIMARY KEY,
    name varchar(255) NOT NULL,
	quantity integer NOT NULL,
	price money NOT NULL,
	id_manufacture integer NOT NULL,
    id_warehouse integer NOT NULL,
	FOREIGN KEY (id_manufacture) REFERENCES manufacture (id),
	FOREIGN KEY (id_warehouse) REFERENCES warehouse (id)
);

INSERT INTO product
VALUES
( '0224327', 'Arm assy right upper front', 1, 31.76, 11001, 71001),
( '0224328', 'Arm assy left upper front', 1, 34.92, 11001, 71001),
( 'L3K913111', 'Gasket intake manifold', 1, 12.67, 11002, 71002),
( 'MPH35', 'Brake pad set rear', 3, 12.92, 11003, 71003),
( '219102H150', 'Engine mount bracket front', 2, 33.73, 11004,71002),
( '872141H000', 'Cover moulding', 2, 2.06, 11004, 71002),
( '553302S150', 'Bracket rear shock absorber', 2, 21.28, 11004,71002),
( '577193Q000', 'Yoke assy rack-support', 1, 22.78, 11004, 71003),
( '565213Q000', 'Bush-rack', 1, 13.56, 11004, 71002),
( '565211H000', 'Bush-rack', 5,  5.22, 11004, 71003),
( '565221H000', 'Yoke assy rack-support', 5, 19.96, 11004, 71003),
( 'L3K910230', 'Gasket engine valve cover', 1, 21.08, 11002, 71002),
( 'MPK34', 'Brake pad set front', 2, 22.36, 11003, 71002),
( '597103W000WK', 'Lever assy parking', 1, 189.92, 11004, 71003),
( '8200768927', 'Filter oil', 1, 5.26, 11007, 71001),
( 'C2X009ABE', 'Brake pad set rear', 1, 16.35, 11012, 71003),
( '319102H000', 'Filter fuel', 5, 15.35, 11004, 71001),
( 'LEDCBCTRL102', 'Control unit CANBUS (set)', 1, 13.25, 11005, 71002),
( '794902G000', 'Checher assy rear right door', 2, 21.12, 11004, 71002),
( 'EFF00193T', 'Filter fuel', 1, 12.98, 11003, 71001),
( 'EG545512E000', 'Bush front arm', 8, 5.13, 11003, 71001),
( '09B62710', 'Brake disc front', 2, 57.15, 11006, 71001),
( 'P83102', 'Brake pad set front', 1, 49.11, 11006, 71001),
( 'GBJK012', 'Ball joint front', 2, 5.34, 11014, 71001),
( 'SB210', 'Filter air', 1, 3.28, 11013, 71003),
( '553112G600', 'Shock absorber rear', 2, 76.67, 11004, 71001),
( '7557YE02B', 'Lamp led PY21 (set)', 1, 26.43, 11005, 71002),
( '64210NBLHCB', 'Lamp H7 Night Breaker (set)', 1, 18.24, 11005, 71001),
( '12060C1', 'Lamp H27W/2', 2, 6.11, 11005, 71002),
( '391802B000', 'Sensor crankshaft position', 1, 32.45, 11004, 71002),
( '971591H150', 'Actuator temperature clima', 1, 27.87, 11004, 71001),
( '30C3750', 'Belt variator transmision', 1, 132.12, 11008, 71001),
( '705400928', 'Cover alloy wheel', 5, 6.78, 11017, 71001),
( '571002P010', 'Power steering pump', 1, 323.56, 11004, 71001),
( '273012B000', 'Coil ignition', 4, 34.99, 11004, 71001),
( 'A1262720725', 'Friction disc A/T', 4, 9.11, 11016, 71001),
( 'MPK23', 'Brake pad set front', 2, 19.32, 11003, 71003),
( 'MPH48', 'Brake pad set front', 1, 23.21, 11003, 71003),
( '04152YZZA6', 'Filter oil', 1, 8.76, 11010, 71001),
( '0888083265', 'Engine oil 0W-20 5L', 1, 65.04, 11010, 71001),
( '2630035504', 'Filter oil', 2, 5.46, 11004, 71001),
( '281133X000', 'Filter air', 1, 11.27, 11004, 71001),
( '24372710822', 'Handbrake parking cable', 2, 34.87, 11015, 71001),
( '496052PA00FFF', 'Joint kit front axle diff. side right', 1, 267.89, 11004, 71003),
( 'TS577401H000', 'Bellows steering gear box', 6, 3.78, 11003, 71003),
( 'AMDJFC88', 'Filter cabin', 1, 4.92, 11009, 71001),
( 'EFF00081T', 'Filter fuel', 1, 17.69, 11003, 71001),
( '495752P000', 'Repair kit - CTR bearing', 1, 134.94, 11004, 71001),
( '243612F000', 'Chain timing', 1, 71.23, 11004, 71001),
( '245102F001', 'Tensioner timing chain', 1, 29.03, 11004, 71001),
( '243512F000', 'Chain timing', 1, 67.99, 11004, 71001),
( '244102F001', 'Tensioner timing chain', 1, 31.08, 11004, 71001),
( 'EX553102B211', 'Shock absorber rear', 2, 48.95, 11003, 71001),
( '243212B200', 'Chain timing', 2, 54.93, 11004,71002),
( '2441025001', 'Tensioner timing chain', 2, 52.81, 11004, 71002),
( 'EG545842E000', 'Bush front arm', 8, 9.98, 11003, 71001),
( '23390YZZHA', 'Filter fuel', 1, 29.87, 11010, 71001),
( 'AMDSB135', 'Bush stabilizer front', 12, 3.23, 11009, 71001),
( 'ECF00017M', 'Filter cabin', 1, 8.77, 11003, 71001),
( 'MPH46', 'Brake pad set front', 1, 23.65, 11003, 71003),
( '552162H00XFFF', 'Bush rear arm', 6, 14.77, 11004, 71003),
( 'MPH45', 'Brake pad set rear', 1, 14.88, 11003, 71003),
( 'MPH38', 'Brake pad set front', 1, 23.88, 11003, 71003),
( 'AMDFC32', 'Filter cabin', 2, 4.12, 11009, 71002),
( '319222B900', 'Filter fuel', 1, 31.12, 11004, 71001),
( '553502Y310FFF', 'Spring rear suspension', 2, 55.34, 11004, 71001),
( 'EX553102P200', 'Shock absorber rear', 2, 49.08, 11003, 71001),
( '08572510', 'Brake disc front', 2, 43.55, 11006, 71001),
( '243804A100', 'Tensioner timing chain', 2, 47.89, 11004, 71001),
( '243514A020', 'Chain timing', 2, 69.87, 11004, 71001),
( '09890421', 'Brake disc front', 2, 57.32, 11006, 71002),
( 'P68036', 'Brake pad set front', 1, 41.05, 11006, 71002),
( 'P68024', 'Brake pad set rear', 1, 29.01, 11006, 71002),
( '552202H000', 'Bolt', 12, 3.75, 11004, 71001),
( '473144B000', 'Seal oil ', 1, 13.77, 11004, 71001),
( 'EX546602P650', 'Shock absorber front right', 1, 78.75, 11003, 71001),
( 'EX546502P650', 'Shock absorber front left', 1, 78.75, 11003, 71001),
( '548302H200', 'Link stabilizer front', 2, 13.89, 11004, 71001),
( 'TSP0155972', 'Compressor ', 1, 376.34, 11011, 71001),
( '553112S400', 'Shock absorber rear', 2, 69.83, 11004, 71001),
( 'EAF00022M', 'Filter air', 1, 5.98, 11003, 71002),
( 'G22C37190A', 'Cover alloy wheel', 2, 7.02, 11002, 71002),
( '957203U100FFF', 'Sensor ultrasonic ', 1, 39.19, 11004, 71001),
( 'L3Y318110', 'Spark plug', 4, 16.43, 11002, 71001),
( '577152S000', 'Yoke assy rack-support', 2, 13.46, 11004, 71001),
( '565552S000', 'Bush-rack', 2, 10.43, 11004, 71001),
( '1780137021', 'Filter air', 1, 24.65, 11010, 71001),
( '8200591283', 'Bracket front shock absorber', 2, 27.86, 11007, 71002),
( '7PK1135', 'Belt ', 1, 13.47, 11008, 71002),
( '8200132259', 'Injectcor fuel gasoline', 2, 43.29, 11007, 71003),
( 'P68050', 'Brake pad set front', 1, 42.66, 11006, 71001),
( '8200492426', 'Spark plug', 4, 16.22, 11007, 71001),
( '8200768913', 'Filter oil', 2, 5.02, 11007,71001),
( '7700845961', 'Filter fuel', 1, 9.44, 11007, 71001),
( '9213CW02B', 'Lamp led W16W (set)', 1, 31.09, 11005, 71001),
( '2850CW02B', 'Lamp led W5W (set)', 1, 19.98, 11005, 71003),
( '90915YZZE1', 'Filter oil', 1, 6.77, 11010, 71002),
( '517202H000', 'Bearing front wheel hub', 2, 37.66, 11004, 71001),
( '1557R02B', 'Lamp led P21/5W (set)', 2, 23.66, 11005, 71002),
( '7507DC02B', 'Lamp silver PY21 (set)', 1, 11.87, 11005, 71002);

CREATE SEQUENCE public.type_id_seq
START WITH 81001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.type 
(
	id integer primary key DEFAULT nextval('public.type_id_seq'::regclass),
	name varchar (50)
);

INSERT INTO type (name)
VALUES
('Legal Entity'),
('Individual Entity');

CREATE SEQUENCE public.client_id_seq
START WITH 21001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.client
(
    id integer primary key DEFAULT nextval('public.client_id_seq'::regclass),
    name varchar (50) NOT NULL,
    surname varchar(50) NOT NULL,
    phone varchar(100) UNIQUE NOT NULL,
    email varchar(100) UNIQUE NOT NULL,
	id_country integer NOT NULL,
    address varchar(255) NOT NULL,
    id_type integer,
	FOREIGN KEY (id_country) REFERENCES country (id),
	FOREIGN KEY (id_type) REFERENCES type (id)
);

INSERT INTO client (name, surname,phone,email,id_country,address,id_type)
VALUES
('Victor', 'Caraman', '+37312121212', 'victor.caraman@gmail.com', 61010, 'Mun. Chisinau, Str. Grenoble 135/9', 81002),
('Vladimir', 'Zlat', '+37313131313', 'vladimir.zlat.gmail.com' ,61010, 'Mun. Chisinau, Str. Bucuresti 91/1 Of.3', 81001),
('Alexandru', 'Frunze', '+37314141414', 'alexandru.frunze@gmail.com', 61010, 'Mun. Chisinau, Bd. Moscova 13/2', 81002),
('Artur', 'Covalenco', '+37315151515', 'artur.covalenco@gmail.com', 61010, 'Mun. Chisinau, Str. Gheorghe Asachi 39/1', 81001),
('Andrei', 'Suvac', '+37316161616', 'andrei.suvac@gmail.com', 61010, 'Mun. Chisinau, Bd. Alba Iulia 117/3', 81002);

CREATE SEQUENCE public.role_id_seq
START WITH 91001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE role
(
	id integer primary key DEFAULT nextval('public.role_id_seq'::regclass),
	name varchar (50) NOT NULL
);

INSERT INTO role (name)
VALUES
('Seller'),
('Sales manager'),
('Administrator');

CREATE SEQUENCE public.users_id_seq
START WITH 51001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.users
(
    id integer primary key DEFAULT nextval('public.users_id_seq'::regclass),
    name varchar (50) NOT NULL,
    surname varchar (50) NOT NULL,
    phone varchar (100) UNIQUE NOT NULL,
    email varchar (100) UNIQUE NOT NULL,
	id_country integer NOT NULL,
    address varchar (100) NOT NULL,
	login varchar (50) UNIQUE NOT NULL,
	password varchar (50) NOT NULL,
	id_role integer NOT NULL,
	FOREIGN KEY (id_country) REFERENCES country (id),
	FOREIGN KEY (id_role) REFERENCES role (id)
);

INSERT INTO users (name,surname,phone,email,id_country,address,login,password,id_role)
VALUES
('Alina', 'Cojocaru', '+37371717171', 'alina.cojocaru@gmail.com', 61010, 'Mun. Chisinau, Bd. I. Gagarin 3', 'a.cojocaru', 'acoj2062', 91001),
('Vlad', 'Matrac', '+37361616161', 'vlad.matrac@gmail.com', 61010, 'Mun. Chisinau, Str. 31 August 145/2', 'v.matrac', 'amat2019', 91002),
('Marin', 'Gotrun', '+37351515151', 'marin.gotrun@gmail.com', 61010, 'Mun. Chisinau, Str. Ion Creanga 34/5', 'm.gotrun', 'mgot2028', 91003);

CREATE SEQUENCE public.purchase_id_seq
START WITH 31001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.purchase
(
    id integer primary key DEFAULT nextval('public.purchase_id_seq'::regclass),
    data date NOT NULL,
	cost money NOT NULL,
    quantity integer NOT NULL,
    id_product varchar(50) NOT NULL,
    id_client integer NOT NULL,
	id_user integer NOT NULL,
	amount money,
	FOREIGN KEY (id_product) REFERENCES product (id),
	FOREIGN KEY (id_client) REFERENCES client (id),
	FOREIGN KEY (id_user) REFERENCES users (id)
);

INSERT INTO purchase (data,cost,quantity,id_product,id_client,id_user)
VALUES
('2017-02-11',33.95,1,'243212B200',21001,51002),
('2017-02-13',24.43,1,'0224327',21002,51002),
('2017-02-13',26.86,1,'0224328',21002,51002),
('2017-02-21',9.85,2,'MPH35',21003,51002),
('2017-02-25',25.93,1,'219102H150',21004,51003),
('2017-02-25',1.53,2,'872141H000',21004,51002),
('2017-03-01',3.44,5,'565211H000',21005,51002),
('2017-03-01',13.68,5,'565221H000',21005,51002),
('2017-03-05',10.02,5,'319102H000',21003,51002),
('2017-03-12',15.62,1,'L3K910230',21001,51002),
('2017-04-04',14.61,2,'MPK34',21001,51002),
('2017-04-09',2.06,6,'TS577401H000',21005,51002),
('2017-04-13',9.45,6,'552162H00XFFF',21002,51003),
('2017-04-27',5.15,8,'EG545842E000',21004,51003),
('2017-04-27',240.19,1,'571002P010',21004,51003),
('2017-05-02',49.26,2,'09890421',21003,51003),
('2017-05-02',34.18,1,'P68036',21003,51002),
('2017-05-06',25.08,1,'P68024',21003,51002),
('2017-05-17',8.16,1,'7507DC02B',21001,51002),
('2017-05-22',8.45,1,'7507DC02B',21001,51003),
('2017-06-09',23.29,2,'2441025001',21002,51002),
('2017-06-19',4.92,1,'90915YZZE1',21004,51003),
('2017-06-21',12.04,4,'8200492426',21005,51002),
('2017-07-03',3.33,2,'8200768913',21005,51003),
('2017-07-17',11.01,1,'7PK1135',21003,51002),
('2017-07-22',64.76,1,'EX546602P650',21003,51002),
('2017-07-22',64.76,1,'EX546502P650',21003,51002),
('2017-08-19',11.11,4,'L3Y318110',21002,51002),
('2017-08-27',6.16,1,'7700845961',21002,51002),
('2017-09-11',18.86,1,'1780137021',21004,51002),
('2018-03-15',38.51,2,'EX553102P200',21001,51002),
('2018-04-23',42.35,2,'553502Y310FFF',21002,51002),
('2018-05-14',99.79,1,'30C3750',21003,51002),
('2018-06-03',26.05,1,'319222B900',21004,51003),
('2018-07-28',285.54,1,'TSP0155972',21005,51002),
('2018-08-16',10.64,2,'548302H200',21001,51003),
('2018-09-11',2.81,12,'552202H000',21002,51003),
('2018-10-29',3.81,2,'2630035504',21003,51003),
('2018-11-17',30.16,1,'957203U100FFF',21004,51002),
('2018-11-29',7.84,4,'A1262720725',21005,51003),
('2018-12-01',10.88,2,'MPH45',21001,51003),
('2018-12-02',2.36,1,'AMDFC32',21002,51003);

update public.purchase set amount = cost * quantity;

CREATE SEQUENCE public.sale_id_seq
START WITH 41001
INCREMENT BY 1
NO MINVALUE
NO MAXVALUE
CACHE 1;

CREATE TABLE public.sales
(
    id integer primary key DEFAULT nextval('public.sale_id_seq'::regclass),
    data date NOT NULL,
	price money NOT NULL,
    quantity integer NOT NULL,
	id_user integer NOT NULL,
    id_product varchar (50) NOT NULL,
	id_client integer NOT NULL,
	amount money,
	FOREIGN KEY (id_product) REFERENCES product (id),
	FOREIGN KEY (id_user) REFERENCES users (id),
	FOREIGN KEY (id_client) REFERENCES client (id)
);

INSERT INTO sales (data,price,quantity,id_user,id_product,id_client)
VALUES
('2017-02-12',54.93,1,51001, '243212B200',21001),
('2017-02-14',31.76,1,51001,'0224327',21001),
('2017-02-14',34.92,1,51001,'0224328',21001),
('2017-02-21',12.92,2,51001,'MPH35',21002),
('2017-02-27',33.73,1,51001,'219102H150',21001),
('2017-02-27',2.06,2,51002,'872141H000',21002),
('2017-03-02',5.22,5,51001,'565211H000',21003),
('2017-03-02',19.96,5,51002,'565221H000',21001),
('2017-03-05',15.35,5,51001,'319102H000',21003),
('2017-03-12',21.08,1,51001,'L3K910230',21001),
('2017-04-04',22.36,2,51002,'MPK34',21004),
('2017-04-09',3.78,6,51003,'TS577401H000',21001),
('2017-04-13',14.77,6,51003,'552162H00XFFF',21001),
('2017-04-27',9.98,8,51003,'EG545842E000',21001),
('2017-04-29',323.56,1,51002,'571002P010',21004),
('2017-05-02',57.32,2,51001,'09890421',21004),
('2017-05-02',41.05,1,51001,'P68036',21002),
('2017-05-07',29.01,1,51001,'P68024',21004),
('2017-05-17',11.87,1,51002,'7507DC02B',21001),
('2017-05-28',12.32,1,51002,'7507DC02B',21005),
('2017-06-11',52.81,2,51001,'2441025001',21005),
('2017-06-19',6.77,1,51003,'90915YZZE1',21002),
('2017-06-21',16.22,4,51001,'8200492426',21002),
('2017-07-03',5.02,2,51001,'8200768913',21004),
('2017-07-17',13.47,1,51003,'7PK1135',21003),
('2017-07-22',78.75,1,51002,'EX546602P650',21004),
('2017-07-22',78.75,1,51002,'EX546502P650',21001),
('2017-08-21',16.43,4,51001,'L3Y318110',21005),
('2017-08-27',9.44,1,51002,'7700845961',21003),
('2017-09-11',24.65,1,51003,'1780137021',21005),
('2018-03-15',49.08,2,51003,'EX553102P200',21002),
('2018-04-24',55.34,2,51002,'553502Y310FFF',21001),
('2018-05-14',132.12,1,51001,'30C3750',21005),
('2018-06-07',31.12,1,51001,'319222B900',21002),
('2018-07-29',376.34,1,51001,'TSP0155972',21005),
('2018-08-16',13.89,2,51002,'548302H200',21001),
('2018-09-11',3.77,12,51002,'552202H000',21005),
('2018-10-29',5.46,2,51003,'2630035504',21005),
('2018-11-18',39.19,1,51001,'957203U100FFF',21002),
('2018-11-29',9.35,4,51001,'A1262720725',21005),
('2018-12-01',14.89,2,51003,'MPH45',21005),
('2018-12-06',4.12,1,51002,'AMDFC32',21005);

update public.sales set amount = price * quantity;


-- PRODUCT TABLE

select pro.id, pro.name, pro.price, pro.quantity, man.name as manufacture,war.name as warehouse
from product pro
inner join manufacture man on pro.id_manufacture = man.id
inner join warehouse war on pro.id_warehouse = war.id


-- Sales

select sal.id, sal.data, concat(cl.name ,' ', cl.surname) as client, 
        pro.id as product_id, sal.price, sal.quantity, sal.amount, 
        concat(us.name,' ', us.surname) as angajat
from sales sal
inner join client cl on sal.id_client = cl.id
inner join product pro on sal.id_product = pro.id
inner join users us on sal.id_user = us.id


-- Purchase 

select pur.id, pur.data, concat(cl.name ,' ', cl.surname) as client,
        pro.id, pur.cost, pur.quantity,pur.amount,
        concat(us.name,' ', us.surname) as angajat
from purchase pur
inner join client cl on pur.id_client = cl.id
inner join product pro on pur.id_product = pro.id
inner join users us on pur.id_user = us.id
	
-- Client

select cl.id, concat(cl.name ,' ', cl.surname) as client,
    cl.phone, cl.email,concat(coun.name,', ', cl.address) as address, type.name as type
from client	cl
inner join country coun on cl.id_country = coun.id
inner join type on cl.id_type = type.id


-- Users

select us.id, concat(us.name,' ', us.surname) as angajat,
    us.phone, us.email, concat(coun.name,', ',us.address) as address,
	role.name as role
from users us
inner join country coun on us.id_country = coun.id
inner join role on us.id_role = role.id

-- Warehouse 

select war.id, war.name, war.phone,concat(coun.name,', ',war.address) as address
from warehouse war
inner join country coun on war.id_country = coun.id

-- Manufacture

select man.id, man.name,concat(coun.name,', ',man.address) as address
from manufacture man
inner join country coun on man.id_country = coun.id
