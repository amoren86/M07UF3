CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                  username VARCHAR(10) NOT NULL,
                  name  VARCHAR(20) NOT NULL,
                  email VARCHAR(50) NOT NULL,
                  rank INT DEFAULT 0,
                  active BOOLEAN DEFAULT true,
                  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);


INSERT INTO users(username,name,email) VALUES ('user1','John Test','john@email.com');
INSERT INTO users(username,name,email) VALUES ('user2','Paul Test','paul@email.com');
