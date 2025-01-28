FROM maven:3.8-openjdk-17-slim

WORKDIR /app
COPY jsf-rabbitmq-producer/pom.xml .
COPY jsf-rabbitmq-producer/src ./src
RUN mvn clean package

CMD ["java", "-jar", "target/jsf-rabbitmq-producer-microbundle.jar"]