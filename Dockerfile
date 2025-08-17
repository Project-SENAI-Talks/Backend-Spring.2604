FROM openjdk:17-jdk-alpine

WORKDIR /app

COPY target/lecture.zero.from.job-0.1.0-SNAPSHOT.jar .

EXPOSE 8080

ENTRYPOINT ["java", "-jar", "lecture.zero.from.job-0.1.0-SNAPSHOT.jar"]