FROM openjdk:8-alpine

COPY target/uberjar/balance.jar /balance/app.jar

EXPOSE 3000

CMD ["java", "-jar", "/balance/app.jar"]
