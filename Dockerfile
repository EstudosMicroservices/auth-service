FROM maven:3.9.9-amazoncorretto-21 AS build
WORKDIR /app
COPY . .

RUN mvn clean package -DskipTests

FROM openjdk:21-jdk
WORKDIR /auth
COPY --from=build /app/target/auth-0.0.1-SNAPSHOT.jar auth.jar
ENTRYPOINT ["java", "-jar", "auth.jar"]