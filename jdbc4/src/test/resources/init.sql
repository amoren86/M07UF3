CREATE TABLE IF NOT EXISTS users(id INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
                  username VARCHAR(10) NOT NULL,
                  name  VARCHAR(20) NOT NULL,
                  email VARCHAR(50) NOT NULL,
                  rank INT DEFAULT 0,
                  active BOOLEAN DEFAULT true,
                  created_on TIMESTAMP DEFAULT CURRENT_TIMESTAMP NOT NULL);


INSERT INTO users(username, name, email) VALUES ('lovelace', 'Ada Lovelace', 'ada@lovelace.was');
INSERT INTO users(username, name, email) VALUES ('babbage', 'Charles Babbage', 'charles@babbage.was');
INSERT INTO users(username, name, email, active) VALUES ('byron', 'Lord Byron', 'lord@byron.was', false);
