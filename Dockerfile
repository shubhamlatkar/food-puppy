#### Stage 1: Build the application
FROM openjdk:16-jdk-alpine as build

# Set the current working directory inside the image
WORKDIR /app

# Copy maven executable to the image
COPY mvnw .
COPY .mvn .mvn

# Copy the pom.xml file
COPY pom.xml .

# Copy the project source
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

COPY account/src account/src
COPY account/pom.xml account/pom.xml

COPY order/src order/src
COPY order/pom.xml order/pom.xml

COPY delivery/src delivery/src
COPY delivery/pom.xml delivery/pom.xml

COPY frontend/src frontend/src
COPY frontend/pom.xml frontend/pom.xml

COPY common/src common/src
COPY common/pom.xml common/pom.xml

COPY common/src/main/java/com/foodgrid/common user/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common restaurant/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common notification/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common account/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common order/src/main/java/com/foodgrid
COPY common/src/main/java/com/foodgrid/common delivery/src/main/java/com/foodgrid

RUN rm notification/src/main/java/com/foodgrid/CommonApplication.java
RUN rm user/src/main/java/com/foodgrid/CommonApplication.java
RUN rm restaurant/src/main/java/com/foodgrid/CommonApplication.java
RUN rm account/src/main/java/com/foodgrid/CommonApplication.java
RUN rm order/src/main/java/com/foodgrid/CommonApplication.java
RUN rm delivery/src/main/java/com/foodgrid/CommonApplication.java

# Build all the dependencies in preparation to go offline.
# This is a separate step so the dependencies will be cached unless
# the pom.xml file has changed.
RUN ./mvnw dependency:go-offline -B


# Package the application
RUN ./mvnw package -DskipTests

RUN mkdir -p configuration/target/dependency && (cd configuration/target/dependency; jar -xf ../*.jar)

RUN mkdir -p eureka/target/dependency && (cd eureka/target/dependency; jar -xf ../*.jar)

RUN mkdir -p user/target/dependency && (cd user/target/dependency; jar -xf ../*.jar)

RUN mkdir -p restaurant/target/dependency && (cd restaurant/target/dependency; jar -xf ../*.jar)

RUN mkdir -p gateway/target/dependency && (cd gateway/target/dependency; jar -xf ../*.jar)

RUN mkdir -p notification/target/dependency && (cd notification/target/dependency; jar -xf ../*.jar)

RUN mkdir -p account/target/dependency && (cd account/target/dependency; jar -xf ../*.jar)

RUN mkdir -p order/target/dependency && (cd order/target/dependency; jar -xf ../*.jar)

RUN mkdir -p delivery/target/dependency && (cd delivery/target/dependency; jar -xf ../*.jar)

RUN mkdir -p frontend/target/dependency && (cd frontend/target/dependency; jar -xf ../*.jar)

#### Stage 2: A  docker image with command to run the eureka
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as configuration

ARG DEPENDENCY=/app/configuration/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8888
ENTRYPOINT ["/bin/sh", "-c", "sleep 40 && java -cp app:app/lib/* com.foodgrid.configuration.ConfigurationApplication"] configuration


#### Stage 2: A  docker image with command to run the eureka
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as eureka

ARG DEPENDENCY=/app/eureka/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8761
ENTRYPOINT ["/bin/sh", "-c", "sleep 60 && java -cp app:app/lib/* com.foodgrid.eureka.EurekaApplication"] eureka

#### Stage 2: A docker image with command to run the notification
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as notification

ARG DEPENDENCY=/app/notification/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8083
ENTRYPOINT ["/bin/sh","-c", "sleep 70 && java -cp app:app/lib/* com.foodgrid.notification.NotificationApplication"] notification

#### Stage 2: A  docker image with command to run the user
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as user

ARG DEPENDENCY=/app/user/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8081
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodgrid.user.UserServiceApplication"] user
# "/bin/sh", "-c", "sleep 10 && java -cp app:app/lib/* com.foodgrid.user.UserServiceApplication"
ENTRYPOINT ["/bin/sh", "-c", "sleep 90 && java -cp app:app/lib/* com.foodgrid.user.UserApplication"] user

#### Stage 2: A docker image with command to run the restaurant
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as restaurant

ARG DEPENDENCY=/app/restaurant/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8082
ENTRYPOINT ["/bin/sh","-c", "sleep 100 && java -cp app:app/lib/* com.foodgrid.restaurant.RestaurantApplication"] restaurant

#### Stage 2: A docker image with command to run the order
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as order

ARG DEPENDENCY=/app/order/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8084
ENTRYPOINT ["/bin/sh","-c", "sleep 110 && java -cp app:app/lib/* com.foodgrid.order.OrderApplication"] order

#### Stage 2: A docker image with command to run the delivery
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as delivery

ARG DEPENDENCY=/app/delivery/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8085
ENTRYPOINT ["/bin/sh","-c", "sleep 120 && java -cp app:app/lib/* com.foodgrid.delivery.DeliveryApplication"] delivery


#### Stage 2: A docker image with command to run the account
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as account

ARG DEPENDENCY=/app/account/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8086
ENTRYPOINT ["/bin/sh","-c", "sleep 130 && java -cp app:app/lib/* com.foodgrid.account.AccountApplication"] account

#### Stage 2: A  docker image with command to run the gateway
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as frontend

ARG DEPENDENCY=/app/frontend/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8090
ENTRYPOINT ["/bin/sh", "-c", "sleep 150 && java -cp app:app/lib/* com.foodgrid.frontend.FrontendApplication"] frontend

#### Stage 2: A  docker image with command to run the gateway
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as gateway

ARG DEPENDENCY=/app/gateway/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "sleep 160 && java -cp app:app/lib/* com.foodgrid.gateway.GatewayApplication"] gateway

