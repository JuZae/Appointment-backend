# Use an ARM-compatible base image for Java
FROM openjdk:17.0.1
VOLUME /tmp
COPY target/A-Point-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-jar", "app.jar"]
