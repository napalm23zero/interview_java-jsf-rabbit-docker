FROM maven:3.8-openjdk-17-slim

WORKDIR /app
COPY jsf-rabbitmq-consumer/pom.xml .
COPY jsf-rabbitmq-consumer/src ./src
RUN mvn clean package

CMD ["java", "-jar", "target/jsf-rabbitmq-consumer-microbundle.jar"]