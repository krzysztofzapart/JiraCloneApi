FROM openjdk:11
ADD target/JiraClone-0.0.1-SNAPSHOT.jar JiraClone.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar","JiraClone.jar"]
