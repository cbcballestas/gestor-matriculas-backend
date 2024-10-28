# Gestión matriculas BACKEND
Backend para gestión de matriculas usando: Spring Boot 3, Spring Webflux, Spring Security, Docker y MongoDB (como motor
de base de datos).

## Requirements
- [JDK 21](https://adoptium.net/es/?variant=openjdk17)
- [Gradle](https://gradle.org/install/)
- [MongoDB](https://www.mongodb.com/try/download/community)

## Aspectos a tener en cuenta ⚠

- Se debe crear la base de datos y alguna colección (Ej: cursos) antes de arrancar la aplicación.
- En el archivo `.env` se encuentran definidos los valores de las variables de entorno usados en el `docker-compose`.
- El archivo JSON con las peticiones se encuentra en la carpeta `postman`. para poder enviar peticiones al backend,
  usted deberá importarlo en un cliente Http ( el usado en éste proyecto fué Postman).
- En la carpeta `db` se encuentra un archivo que contiene documentos de MongoDB para insertarlos en las siguientes
  colecciones:
```shell
    - roles
    - usuarios
    - menus
```
- Para ejecutar el `docker-compose` se DEBE correr el siguiente comando ( en la ruta raíz del proyecto)
``` shell
docker-compose up
```
- Para crear la imágen en docker se debe correr el siguiente comando ( en la ruta raíz del proyecto):
``` shell
docker build -t gestion-matriculas-backend .
```

You will need to set up your database (or create your own) with the following configuration
``` yaml
spring:
  application:
    name: gestion-matriculas
  data:
    mongodb:
      uri: ${DATABASE_URL:mongodb://localhost:27017/gestion_matriculas}

jjwt:
  secret:

routing:
  cursos-path: ${CURSOS_PATH:/api/v2/cursos}
  estudiantes-path: ${ESTUDIANTES_PATH:/api/v2/estudiantes}
  registro-path: ${REGISTROS_PATH:/api/v2/registro-matricula}

```

## Run application

There are several ways to run a Spring Boot application in your local machine. The most common way is executing `main` method in `com.cballestas.gestion_matriculas.GestionMatriculasApplication` class from your IDE

Alternative you can run:

```shell
./gradlew bootRun
```