# Étape 1 : Compilation avec Maven 3 et Java 25 (Validé sur Docker Hub)
FROM maven:3-eclipse-temurin-25 AS build
WORKDIR /bank-queue-management-api
COPY . .
RUN ./mvnw clean package -DskipTests

# Étape 2 : Exécution avec la machine virtuelle Java 25
FROM eclipse-temurin:25-jdk
WORKDIR /bank-queue-management-api
COPY --from=build /bank-queue-management-api/target/*.jar app.jar
EXPOSE 2026
ENTRYPOINT ["java", "-jar", "app.jar"]
