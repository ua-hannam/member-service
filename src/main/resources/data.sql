INSERT INTO role (role_name)
VALUES ('회원');
INSERT INTO role (role_name)
VALUES ('관리자');
INSERT INTO role (role_name)
VALUES ('사장님');

INSERT INTO member (role_id, name, email, password, nickname, contact, balance)
VALUES (1, 'John Doe', 'john.doe@example.com', 'encryptedPassword', 'JDoe', '123-456-7890', 100);
