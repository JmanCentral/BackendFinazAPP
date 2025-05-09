#IMAGEN DEL CONTENEDOR

FROM eclipse-temurin:17.0.14_7-jdk

#INFORMAR EL PUERTO DONDE SE EJECUTA EL PUERTO

EXPOSE 8862

#DEFINIR DIRECTORIO RAIZ DEL CONTENEDOR

WORKDIR /root

#COPIAR Y PEGAR ARCHIVOS DENTRO DEL CONTENEDO
COPY ./pom.xml /root
COPY  ./.mvn /root/.mvn
COPY ./mvnw /root

#Descargar las dependencias
RUN ./mvnw dependency:go-offline

#COPIAR EL CÓDIGO FUENTE DENTRO DEL CONTENEDOR
COPY ./src /root

#CONSTRUIR NUESTRA APLICACIÓN
RUN ./mvnw clean install

#LEVANTAR NUESTRA APLICACIÓN CUANDO EL CONTENEDOR INICIE

ENTRYPOINT ["java","-jar","/root/target/finanzapp-0.0.1-SNAPSHOT.jar"]