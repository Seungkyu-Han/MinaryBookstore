FROM openjdk:17-jdk

ARG JAR_FILE=build/libs/minarybook-0.0.1-SNAPSHOT.war

WORKDIR /minary

COPY ${JAR_FILE} minarybook.jar

EXPOSE 8080

CMD ["java", "-jar", "minarybook.jar"]