FROM ://redhat.com/ubi9/openjdk-25:1.17
VOLUME /tmp
COPY target/*.jar  app.jar
ENTRYPOINT ["java","-jar", "app.jar"]