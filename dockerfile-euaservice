FROM openjdk:17.0-jdk
RUN mkdir /APP
WORKDIR /APP
COPY EUAclient/target/euaService-0.0.1-SNAPSHOT.jar  /APP/euaService-0.0.1-SNAPSHOT.jar
COPY opentelemetry-javaagent.jar /APP/opentelemetry-javaagent.jar
EXPOSE 8901
RUN chmod -R 777 /APP
ENTRYPOINT ["java","-jar","-javaagent:/APP/opentelemetry-javaagent.jar","-Dotel.metrics.exporter=none","-Dotel.resource.attributes="service.name=euaService-app"","-Dotel.exporter.otlp.endpoint=http://100.65.158.41:4317","/APP/euaService-0.0.1-SNAPSHOT.jar","--spring.config.location=application.properties"]
