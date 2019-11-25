FROM tomcat:9-jre11-slim

RUN rm -rf /usr/local/tomcat/webapps/ROOT

ADD target/client.war /usr/local/tomcat/webapps/ROOT.war

