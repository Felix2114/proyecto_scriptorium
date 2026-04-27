FROM openjdk:17-jdk-slim

# Directorio de trabajo dentro del contenedor
WORKDIR /app

# Copia el archivo JAR generado
COPY target/*.jar app.jar

# Variables de entorno (puedes redefinirlas al correr el contenedor)
ENV DB_URL=""
ENV DB_USERNAME=""
ENV DB_PASSWORD=""

# Ejecuta el JAR
ENTRYPOINT ["java", "-jar", "app.jar"]
