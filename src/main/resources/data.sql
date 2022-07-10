INSERT INTO user (email, first_name, last_name, pass_encoded, role) VALUES
('andrey95sevas@gmail.com', 'Andrii', 'Martishyn', '$2a$10$V0dEaSHPZe24gMfzRCggvuQu0eD7HSEgcsfSe8F6IRxzaC2m5uWti', 'ADMIN'),
('customer@gmail.com', 'Andrii', 'Mart', '$2a$10$V0dEaSHPZe24gMfzRCggvuQu0eD7HSEgcsfSe8F6IRxzaC2m5uWti','CUSTOMER');

INSERT INTO station (name, code) VALUES
("Ivano-Frankivsk", "IFR"),
("Lviv-South", "LVV"),
("Brody-Central", "BDY"),
("Kryvyn-South", "KVN"),
("Shepetivka-South", "SPK"),
("Kyiv-Pass", "KVP");

INSERT INTO train_model(model) VALUES
("Tarpan"),
("Hyundai Rotem"),
("Skoda EJ-675");

INSERT INTO train(id, model_id) VALUES
(200, 1),
(125, 2),
(300, 3);