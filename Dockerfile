FROM eclipse-temurin:17-jdk

ARG SERVICE_NAME
ARG SERVICE_VERSION
ARG JAR_FILE={$SERVICE_NAME}/target/{$SERVICE_NAME}-{$SERVICE_VERSION}.jar

RUN mkdir /opt/app

COPY ${JAR_FILE} /opt/app/app.jar

EXPOSE 8080

ENTRYPOINT ["java", "-Xmx128m", "-Xms128m", "-jar", "/opt/app/app.jar"]