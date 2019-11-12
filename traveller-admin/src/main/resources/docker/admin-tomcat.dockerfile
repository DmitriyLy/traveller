FROM tomcat:9-jre10-slim

RUN rm -rf /usr/local/tomcat/webapps/ROOT

ADD target/admin.war /usr/local/tomcat/webapps/ROOT.war