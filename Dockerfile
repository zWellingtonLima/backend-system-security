# STAGE 1 — Build Maven
FROM maven:3.9.6-eclipse-temurin-21 AS builder
WORKDIR /app

COPY pom.xml .
RUN mvn dependency:go-offline -B

COPY src ./src
RUN mvn clean package -DskipTests

# STAGE 2 — Final image (leve)
FROM eclipse-temurin:21-jre-alpine
WORKDIR /app

# Copia o jar gerado do stage 1
COPY --from=builder /app/target/*.jar app.jar

# Railway usa variável PORT → você deve passar para o Spring Boot
ENV PORT=8080
EXPOSE 8080

ENTRYPOINT ["java", "-jar", "app.jar"]