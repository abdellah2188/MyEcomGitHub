FROM openjdk:21-jdk
VOLUME /products/images
#COPY ../VOLUME_IMAGE products/images/
COPY ecommerce/products/unknown.png products/images/
COPY target/*.jar  app.jar
ENTRYPOINT ["java","-jar", "app.jar"]