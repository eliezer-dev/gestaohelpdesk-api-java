FROM openjdk:21
WORKDIR /app
COPY ./target/gestao-helpdesk-0.0.4-dev.jar /app
EXPOSE 8080
CMD ["java", "-jar", "gestao-helpdesk-0.0.4-dev.jar"]