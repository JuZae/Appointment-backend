FROM openjdk:17
VOLUME /tmp
COPY target/A-Point-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java", "-Dspring.profiles.active=dev",  "-jar", "app.jar"]
