CREATE DATABASE traveller;

CREATE USER 'traveller'@'%' identified by 'traveller';

GRANT ALL ON traveller.* TO 'traveller'@'%' IDENTIFIED BY 'traveller';

FLUSH PRIVILEGES;