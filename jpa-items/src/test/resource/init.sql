CREATE TABLE IF NOT EXISTS items(reference VARCHAR(50) PRIMARY KEY NOT NULL,
                  name VARCHAR(50) NOT NULL,
                  description  VARCHAR(250),
                  price DOUBLE NOT NULL,
                  image VARCHAR(255),
                  stock INT DEFAULT 0);                            