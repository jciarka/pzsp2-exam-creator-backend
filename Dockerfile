# usage example - must define VERSION variable
# prerequisits:
#           mvnw clean install
# build:
#           docker build -t pzsp03 .
# run:
#           docker run --name pzsp03 -d -p 8090:8090 pzsp03

#push:
#           docker tag pzsp03 jciarka/pzsp03:version
#           docker push jciarka/pzsp03:version

FROM openjdk:11
ARG VERSION
ARG JAR_FILE=target/*.jar
COPY ${JAR_FILE} app.jar
ENTRYPOINT ["java","-jar","/app.jar"]