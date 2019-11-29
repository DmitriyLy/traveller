FROM node:10-slim as node10

RUN apt-get update && apt-get install -y bzip2

COPY client/package.json /opt/client/package.json
WORKDIR /opt/client/

RUN yarn install

COPY client/ /opt/client

RUN node_modules/.bin/ng build --prod 

FROM httpd:2

COPY --from=node10 /opt/client/dist/ /usr/local/apache2/htdocs/ 