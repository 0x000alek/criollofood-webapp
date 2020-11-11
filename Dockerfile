FROM openjdk:8-jdk-alpine
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} criollofood-webapp.jar
ENTRYPOINT ["java","-jar","/criollofood-webapp.jar"]
