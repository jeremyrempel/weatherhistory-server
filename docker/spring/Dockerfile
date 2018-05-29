FROM openjdk:10-jre-slim
EXPOSE 8080
VOLUME /tmp
COPY app.jar app.jar
ENTRYPOINT ["java","-Djava.security.egd=file:/dev/./urandom","-jar","/app.jar"]
