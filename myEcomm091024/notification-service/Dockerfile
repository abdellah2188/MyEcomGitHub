FROM openjdk:25-ea-21-jdk-slim
COPY target/*.jar  app.jar
ENTRYPOINT ["java","-jar", "app.jar"]