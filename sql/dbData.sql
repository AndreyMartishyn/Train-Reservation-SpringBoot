
spring.jpa.properties.javax.persistence.schema-generation.create-source=metadata
spring.jpa.properties.javax.persistence.schema-generation.scripts.action=create
spring.jpa.properties.javax.persistence.schema-generation.scripts.create-target=create.sql


--encoded b-crypt password is 'Password1' as decoded, for login purposes for both users

INSERT INTO user (email, first_name, last_name, pass_encoded, role) VALUES
('andrey95sevas@gmail.com', 'Andrii', 'Martishyn', '$2a$10$V0dEaSHPZe24gMfzRCggvuQu0eD7HSEgcsfSe8F6IRxzaC2m5uWti', 'ADMIN'),
('customer@gmail.com', 'Andrii', 'Mart', '$2a$10$V0dEaSHPZe24gMfzRCggvuQu0eD7HSEgcsfSe8F6IRxzaC2m5uWti','CUSTOMER');


INSERT INTO station (name, code) VALUES
("Ivano-Frankivsk", "IFR"),
("Lviv-South", "LVV"),
("Brody-Central", "BDY"),
("Kryvyn-South", "KVN"),
("Shepetivka-Sout", "SPK"),
("Kyiv-Pas", "KVP");


INSERT INTO train_model(model) VALUES
("Tarpan"),
("Hyundai Rotem"),
("Skoda EJ-675");

INSERT INTO train(id, model_id) VALUES
(200, 1),
(125, 2),
(300, 3),


insert into route values (146, 125);


INSERT INTO route_point(arrival, departure, route_id, station_id) VALUES
('2022-08-12 22:44', '2022-08-12 22:45', 146, 2),
('2022-08-12 22:54', '2022-08-12 22:55', 146, 3),
('2022-08-12 23:44', '2022-08-12 23:45', 146, 4),
('2022-08-12 23:50', '2022-08-12 23:53', 146, 5),
('2022-08-12 23:56', '2022-08-12 23:59', 146, 6)
