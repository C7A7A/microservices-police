FROM openjdk:17
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} vehicles.jar
ENTRYPOINT ["java", "-jar", "/vehicles.jar"]