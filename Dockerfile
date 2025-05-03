# Usa imagem do Maven com JDK 21 para construção
FROM maven:3.9.6-eclipse-temurin-21 as build

WORKDIR /app
COPY . .
RUN mvn clean package -DskipTests

# Usa imagem do Java puro para rodar
FROM eclipse-temurin:21-jdk
WORKDIR /app

# Definir variáveis de ambiente
ENV DATABASE_URL=jdbc:h2:mem:testdb
ENV JAVA_OPTS=-Xmx1024m
ENV API_KEY=your-api-key-here

COPY --from=build /app/target/clinic-0.0.1-SNAPSHOT.jar app.jar

EXPOSE 8081

# Comando para rodar a aplicação
CMD ["java", "-jar", "app.jar"]
