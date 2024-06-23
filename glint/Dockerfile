FROM amazoncorretto:17
EXPOSE 8080
ARG JAR_FILE=./build/libs/*.jar
COPY ${JAR_FILE} glint.jar
ENTRYPOINT ["java", "-jar", "/glint.jar"]