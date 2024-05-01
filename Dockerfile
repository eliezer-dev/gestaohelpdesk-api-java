FROM openjdk:21
WORKDIR /app
COPY ./target/gestao-helpdesk-0.0.8.jar /app
EXPOSE 8080
CMD ["java", "-jar", "gestao-helpdesk-0.0.8.jar"]