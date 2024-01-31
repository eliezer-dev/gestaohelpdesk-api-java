FROM openjdk:21
WORKDIR /app
COPY ./target/superticket-0.0.3-dev.jar /app
EXPOSE 8080
CMD ["java", "-jar", "superticket-0.0.3-dev.jar"]