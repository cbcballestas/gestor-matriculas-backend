FROM eclipse-temurin:21-alpine
WORKDIR /gestion-matriculas
CMD ["./gradlew", "clean", "build"]
COPY build/libs/gestion-matriculas.jar app.jar

#EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]