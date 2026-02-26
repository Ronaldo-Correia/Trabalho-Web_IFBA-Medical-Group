# multi-stage build for the IFBA Medical Group clinic application

# --- build stage -----------------------------------------------------------
FROM docker.io/library/maven:3.9.3-openjdk-21 AS build
WORKDIR /workspace

# cache dependencies by copying only the pom first
COPY pom.xml ./
RUN mvn dependency:go-offline -B

# copy sources and build
COPY src ./src
RUN mvn clean package -DskipTests -B

# --- runtime stage ---------------------------------------------------------
FROM docker.io/library/openjdk:21-jre
WORKDIR /app

# copy jar from the build stage
COPY --from=build /workspace/target/clinic-0.0.1-SNAPSHOT.jar app.jar

# expose the port configured in application.yaml
EXPOSE 8082

ENTRYPOINT ["java","-jar","/app/app.jar"]
