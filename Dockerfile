# Utiliser l'image officielle de Amazon Corretto JDK 17
FROM amazoncorretto:17-alpine-jdk

# Ajouter un répertoire pour l'application
VOLUME /tmp

# Argument pour définir le nom de l'application JAR
ARG JAR_FILE=target/foot-service-0.0.1-SNAPSHOT.jar

# Copier le fichier JAR généré par Maven dans le conteneur
COPY ${JAR_FILE} /app/foot-service-0.0.1-SNAPSHOT.jar

# Exposer le port 8081
EXPOSE 8081

# Commande pour démarrer l'application Spring Boot
ENTRYPOINT ["java", "-jar", "/app/foot-service-0.0.1-SNAPSHOT.jar"]
