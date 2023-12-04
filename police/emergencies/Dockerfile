FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} emergencies.jar
ENTRYPOINT ["java", "-jar", "/emergencies.jar"]