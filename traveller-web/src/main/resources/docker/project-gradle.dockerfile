FROM gradle:5.1-jdk11-slim as gradle5

COPY build.gradle /home/gradle/build.gradle
COPY settings.gradle /home/gradle/settings.gradle
COPY gradle.properties /home/gradle/gradle.properties
COPY germes-common/build.gradle /home/gradle/germes-common/build.gradle
COPY germes-admin/build.gradle /home/gradle/germes-admin/build.gradle
COPY germes-model/build.gradle /home/gradle/germes-model/build.gradle
COPY germes-persistence/build.gradle /home/gradle/germes-persistence/build.gradle
COPY germes-rest/build.gradle /home/gradle/germes-rest/build.gradle
COPY germes-service/build.gradle /home/gradle/germes-service/build.gradle
COPY germes-web/build.gradle /home/gradle/germes-web/build.gradle
COPY germes-ticket-service/build.gradle /home/gradle/germes-ticket-service/build.gradle
COPY germes-trip-service/build.gradle /home/gradle/germes-trip-service/build.gradle
COPY germes-user-service/build.gradle /home/gradle/germes-user-service/build.gradle
COPY germes-geography-service/build.gradle /home/gradle/germes-geography-service/build.gradle

USER root

RUN gradle -PskipAngular downloadDependencies

COPY . /home/gradle/

RUN gradle -PskipAngular clean build && cp /home/gradle/germes-geography-service/build/libs/geography-service.war /opt && \
    cp /home/gradle/germes-admin/build/libs/admin.war /opt && \
    rm -rf /home/gradle/germes*
