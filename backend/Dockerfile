FROM openjdk:11-jdk
EXPOSE 8080
COPY backend/build/libs/*.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"] 
