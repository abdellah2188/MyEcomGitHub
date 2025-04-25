FROM openjdk:21-jdk
VOLUME /tmp
COPY target/*.jar  app.jar
COPY ecommerce ecommerce
ENTRYPOINT ["java","-jar", "app.jar"]