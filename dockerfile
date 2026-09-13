# Étape 1 : Compiler l'application avec Maven
FROM maven:3.9.6-eclipse-temurin-25 AS build
WORKDIR /bank-queue-management-api
COPY . .
RUN ./mvnw clean package -DskipTests

# Étape 2 : Exécuter l'application avec Java
FROM eclipse-temurin:25-jre-jammy
WORKDIR /app
COPY --from=build /app/target/*.jar app.jar
EXPOSE 2026
ENTRYPOINT ["java", "-jar", "bank-queue-management-api.jar"]
