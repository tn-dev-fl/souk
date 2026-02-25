FROM  dhi.io/eclipse-temurin:21-alpine3.22
WORKDIR /app
COPY target/*.jar app.jar
ENTRYPOINT ["java","-jar","app.jar"]

EXPOSE 9090