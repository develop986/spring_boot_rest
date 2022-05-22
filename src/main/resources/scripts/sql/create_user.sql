CREATE USER IF NOT EXISTS 'em_user'@'localhost'
  IDENTIFIED BY 'em_pass'
  PASSWORD EXPIRE NEVER;

GRANT ALL ON em_db.* TO 'em_user'@'localhost';