FROM mysql:5.7
LABEL Author="dmly", Version="0.1"

COPY ./traveller-admin/src/main/resources/docker/grant.sql /docker-entrypoint-initdb.d/

ENV MYSQL_ROOT_PASSWORD=root MYSQL_USER=traveller MYSQL_PASSWORD=traveller MYSQL_DATABASE=traveller
