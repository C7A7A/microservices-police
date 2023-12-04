FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} policemen.jar
ENTRYPOINT ["java", "-jar", "/policemen.jar"]