FROM gradle:8.9.0-jdk21-alpine as base
WORKDIR /app
COPY . .

FROM base as build
RUN gradle build --no-daemon --console=verbose

FROM openjdk:21-jdk as production
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar

ENTRYPOINT ["java", "-jar", "/app/app.jar"]