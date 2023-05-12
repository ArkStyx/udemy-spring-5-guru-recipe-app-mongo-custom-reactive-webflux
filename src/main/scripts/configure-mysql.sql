CREATE DATABASE IF NOT EXISTS sfg_dev;
CREATE DATABASE IF NOT EXISTS sfg_prod;

CREATE USER IF NOT EXISTS 'sfg_dev_user'@'localhost' IDENTIFIED BY 'guru';
CREATE USER IF NOT EXISTS 'sfg_prod_user'@'localhost' IDENTIFIED BY 'guru';

CREATE USER IF NOT EXISTS 'sfg_dev_user'@'%' IDENTIFIED BY 'guru';
CREATE USER IF NOT EXISTS 'sfg_prod_user'@'%' IDENTIFIED BY 'guru';

GRANT SELECT, INSERT, UPDATE, DELETE ON sfg_dev.* TO 'sfg_dev_user'@'localhost';
GRANT SELECT, INSERT, UPDATE, DELETE ON sfg_prod.* TO 'sfg_prod_user'@'localhost';

GRANT SELECT, INSERT, UPDATE, DELETE ON sfg_dev.* TO 'sfg_dev_user'@'%';
GRANT SELECT, INSERT, UPDATE, DELETE ON sfg_prod.* TO 'sfg_prod_user'@'%';
