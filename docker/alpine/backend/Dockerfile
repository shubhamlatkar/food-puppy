FROM openjdk:16-jdk-alpine as build

WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

COPY pom.xml .

COPY eureka/src eureka/src
COPY eureka/pom.xml eureka/pom.xml

COPY user/src user/src
COPY user/pom.xml user/pom.xml


COPY restaurant/src restaurant/src
COPY restaurant/pom.xml restaurant/pom.xml


COPY gateway/src gateway/src
COPY gateway/pom.xml gateway/pom.xml

COPY configuration/src configuration/src
COPY configuration/pom.xml configuration/pom.xml

COPY notification/src notification/src
COPY notification/pom.xml notification/pom.xml

COPY accounts/src accounts/src
COPY accounts/pom.xml accounts/pom.xml

COPY order/src order/src
COPY order/pom.xml order/pom.xml

COPY delivery/src delivery/src
COPY delivery/pom.xml delivery/pom.xml

COPY common/src common/src
COPY common/pom.xml common/pom.xml

COPY common/src/main/java/com/foodgrid/common user/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common restaurant/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common notification/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common accounts/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common order/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common delivery/src/main/java/com/foodgrid

RUN rm notification/src/main/java/com/foodgrid/CommonApplication.java
RUN rm user/src/main/java/com/foodgrid/CommonApplication.java
RUN rm restaurant/src/main/java/com/foodgrid/CommonApplication.java
RUN rm accounts/src/main/java/com/foodgrid/CommonApplication.java
RUN rm order/src/main/java/com/foodgrid/CommonApplication.java
RUN rm delivery/src/main/java/com/foodgrid/CommonApplication.java

# Individual stage executable
# docker build --target ui -t frontend:latest .
# docker create -ti --name frontend-container frontend bash
#  docker container ls -a
# docker cp  frontend-container:/frontend/delivery/build ~/foodgrid/frontend/delivery/build
# java
# docker build --target build -t java:latest .
# docker create -ti --name java-container java bash
# docker cp  java-container:/app/delivery/target ~/


# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B

# Package the application
RUN ./mvnw package -DskipTests

