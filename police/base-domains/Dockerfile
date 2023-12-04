FROM openjdk:17
ARG JAR_FILE=build/libs/*.jar
COPY ${JAR_FILE} base-domains.jar
ENTRYPOINT ["java", "-jar", "/base-domains.jar"]