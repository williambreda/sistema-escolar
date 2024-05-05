# Estágio de compilação
FROM maven:3.8.3-openjdk-17 AS builder
RUN mkdir -p /tmp/src/app
WORKDIR /tmp/src/app
COPY .. .
RUN mvn package -Dmaven.test.skip=true

# Estágio final
FROM eclipse-temurin:17.0.11_9-jre-jammy
RUN mkdir -p /app
WORKDIR /app
COPY --from=builder /tmp/src/app/target/sistema-escolar-1.0.jar sistema-escolar.jar
CMD ["sh", "-c", "java -jar sistema-escolar.jar"]