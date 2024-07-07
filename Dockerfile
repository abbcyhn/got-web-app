FROM jelastic/maven:3.9.5-openjdk-21 AS build

WORKDIR /app

COPY pom.xml .
COPY checkstyle.xml .
COPY spotbugs-exclude.xml .
COPY src ./src

RUN mvn clean package -DskipTests

FROM jelastic/maven:3.9.5-openjdk-21

WORKDIR /app

COPY --from=build /app/target/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
