FROM eclipse-temurin:18-jre-alpine
RUN mkdir /opt/app/
RUN mkdir -p /var/log/
COPY ./build/lib/*.jar /opt/app/app.jar
EXPOSE 9000
ENTRYPOINT ["java", "-jar", "/opt/app/app.jar", "--service.google.credentials=${GOOGLE_CREDENTIALS}", "--logging.level.root=${LOGLEVEL}", "--spring.data.mongodb.uri=${MONGOURI}"]
