FROM jetty:9.4

COPY traveller-admin/target/admin.war /var/lib/jetty/webapps/
COPY traveller-admin/src/main/resources/admin.xml /var/lib/jetty/webapps/