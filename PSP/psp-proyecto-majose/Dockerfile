FROM maven:3.9.9-eclipse-temurin-23 AS compilacion

WORKDIR /app

COPY pom.xml .
COPY src ./src

RUN mvn clean package

FROM eclipse-temurin:23-jre

WORKDIR /app

COPY --from=compilacion /app/target/classes ./clases

CMD ["java", "-cp", "clases", "vista.main"]

