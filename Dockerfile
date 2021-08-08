#### Stage 1: Build the frontend
FROM node:10.24.1-alpine3.11 as ui

WORKDIR /frontend

COPY user/src/main/frontend user/

COPY restaurant/src/main/frontend restaurant/

COPY delivery/src/main/frontend delivery/

WORKDIR /frontend/user

RUN npm i

RUN npm run build

WORKDIR /frontend/restaurant

RUN npm i

RUN npm run build

WORKDIR /frontend/delivery

RUN npm i

RUN npm run build

#### Stage 2: Build the application
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

COPY --from=ui /frontend/user/build user/src/main/resources/static
RUN rm -r user/src/main/frontend

COPY restaurant/src restaurant/src
COPY restaurant/pom.xml restaurant/pom.xml

COPY --from=ui /frontend/restaurant/build restaurant/src/main/resources/static
RUN rm -r restaurant/src/main/frontend

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

COPY --from=ui /frontend/delivery/build delivery/src/main/resources/static
RUN rm -r delivery/src/main/frontend

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

RUN mkdir -p accounts/target/dependency && (cd accounts/target/dependency; jar -xf ../*.jar)

RUN mkdir -p order/target/dependency && (cd order/target/dependency; jar -xf ../*.jar)

RUN mkdir -p delivery/target/dependency && (cd delivery/target/dependency; jar -xf ../*.jar)

#### Stage 3: A  docker image with command to run the configuration
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as configuration

ARG DEPENDENCY=/app/configuration/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8888
ENTRYPOINT ["/bin/sh", "-c", "sleep 30 && java -cp app:app/lib/* com.foodgrid.configuration.ConfigurationApplication"] configuration

#### Stage 4: A  docker image with command to run the eureka
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as eureka

ARG DEPENDENCY=/app/eureka/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8761
ENTRYPOINT ["/bin/sh", "-c", "sleep 50 && java -cp app:app/lib/* com.foodgrid.eureka.EurekaApplication"] eureka

#### Stage 5: A docker image with command to run the order
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as order

ARG DEPENDENCY=/app/order/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8084
ENTRYPOINT ["/bin/sh","-c", "sleep 70 && java -cp app:app/lib/* com.foodgrid.order.OrderApplication"] order

#### Stage 6: A docker image with command to run the accounts
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as accounts

ARG DEPENDENCY=/app/accounts/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8086
ENTRYPOINT ["/bin/sh","-c", "sleep 80 && java -cp app:app/lib/* com.foodgrid.accounts.AccountsApplication"] accounts

#### Stage 7: A docker image with command to run the notification
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as notification

ARG DEPENDENCY=/app/notification/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8083
ENTRYPOINT ["/bin/sh","-c", "sleep 90 && java -cp app:app/lib/* com.foodgrid.notification.NotificationApplication"] notification

#### Stage 8: A  docker image with command to run the user
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as user

ARG DEPENDENCY=/app/user/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8081
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodgrid.user.UserServiceApplication"] user
# "/bin/sh", "-c", "sleep 10 && java -cp app:app/lib/* com.foodgrid.user.UserServiceApplication"
ENTRYPOINT ["/bin/sh", "-c", "sleep 120 && java -cp app:app/lib/* com.foodgrid.user.UserApplication"] user


#### Stage 9: A docker image with command to run the restaurant
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as restaurant

ARG DEPENDENCY=/app/restaurant/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8082
ENTRYPOINT ["/bin/sh","-c", "sleep 130 && java -cp app:app/lib/* com.foodgrid.restaurant.RestaurantApplication"] restaurant

#### Stage 10: A docker image with command to run the delivery
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as delivery

ARG DEPENDENCY=/app/delivery/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8085
ENTRYPOINT ["/bin/sh","-c", "sleep 140 && java -cp app:app/lib/* com.foodgrid.delivery.DeliveryApplication"] delivery

#### Stage 11: A  docker image with command to run the gateway
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as gateway

ARG DEPENDENCY=/app/gateway/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "sleep 160 && java -cp app:app/lib/* com.foodgrid.gateway.GatewayApplication"] gateway
