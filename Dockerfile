# Этап сборки
FROM gradle:8.13.0-jdk21 AS build
WORKDIR /app
COPY . .
RUN gradle shadowJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/RMP-user-service-all.jar app.jar

EXPOSE 8080
CMD ["java", "-jar", "app.jar"]