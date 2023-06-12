CREATE TABLE IF NOT EXISTS items(reference VARCHAR(50) PRIMARY KEY NOT NULL,
                  name VARCHAR(50) NOT NULL,
                  description  VARCHAR(250),
                  price DOUBLE NOT NULL,
                  image VARCHAR(255),
                  stock INT DEFAULT 0);
                  
INSERT INTO items(reference, name, description, price, image, stock) 
VALUES ('A0000001', 'Big Pig', 'Cerdito bonito y apestoso de gran calidad', 23.90, 'cerdo.jpg', 5350);

INSERT INTO items(reference, name, description, price, image, stock)
VALUES ('A0000002', 'Silly Monkey', 'Simpático monito que hará las delícias de los más pequeños', 24.90, 'mono.jpg', 2491);

INSERT INTO items(reference, name, description, price, image, stock)
VALUES ('A0000003', 'Fat Bear', 'El oso amoroso que le hará coger el sueño con sólo abrazarlo', 25.00, 'oso.jpg', 88);

INSERT INTO items(reference, name, description, price, image, stock)
VALUES ('A0000004', 'Vaca Paca', 'La vaca más realista del mundo de los peluches. Sólo le falta andar', 23.80, 'vaca.jpg', 150);