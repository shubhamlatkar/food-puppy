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

COPY common/src common/src
COPY common/pom.xml common/pom.xml

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

RUN mkdir -p common/target/dependency && (cd common/target/dependency; jar -xf ../*.jar)

ARG COMMON_DEPENDENCY=/app/common/target/dependency

#### Stage 2: A  docker image with command to run the eureka
FROM openjdk:16-jdk-alpine as configuration

ARG DEPENDENCY=/app/configuration/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8888
ENTRYPOINT ["/bin/sh", "-c", "sleep 40 && java -cp app:app/lib/* com.foodgrid.configuration.ConfigurationApplication"] configuration


#### Stage 2: A  docker image with command to run the eureka
FROM openjdk:16-jdk-alpine as eureka

ARG DEPENDENCY=/app/eureka/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8761
ENTRYPOINT ["/bin/sh", "-c", "sleep 60 && java -cp app:app/lib/* com.foodgrid.eureka.EurekaApplication"] eureka

#### Stage 2: A  docker image with command to run the user
FROM openjdk:16-jdk-alpine as user

ARG DEPENDENCY=/app/user/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8081
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodgrid.user.UserServiceApplication"] user
# "/bin/sh", "-c", "sleep 10 && java -cp app:app/lib/* com.foodgrid.user.UserServiceApplication"
ENTRYPOINT ["/bin/sh", "-c", "sleep 80 && java -cp app:app/lib/* com.foodgrid.user.UserApplication"] user

#### Stage 2: A docker image with command to run the restaurant
FROM openjdk:16-jdk-alpine as restaurant

ARG DEPENDENCY=/app/restaurant/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/
COPY --from=build ${COMMON_DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8082
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodgrid.restaurant.RestaurantServiceApplication"] restaurant
ENTRYPOINT ["/bin/sh","-c", "sleep 100 && java -cp app:app/lib/* com.foodgrid.restaurant.RestaurantApplication"] restaurant

#### Stage 2: A  docker image with command to run the gateway
FROM openjdk:16-jdk-alpine as gateway

ARG DEPENDENCY=/app/gateway/target/dependency

# Copy project dependencies from the build stage
COPY --from=build ${DEPENDENCY}/BOOT-INF/lib /app/lib
COPY --from=build ${DEPENDENCY}/META-INF /app/META-INF
COPY --from=build ${DEPENDENCY}/BOOT-INF/classes /app/
COPY --from=build ${COMMON_DEPENDENCY}/BOOT-INF/classes /app/
COPY --from=build ${COMMON_DEPENDENCY}/BOOT-INF/classes /app/

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "sleep 120 && java -cp app:app/lib/* com.foodgrid.gateway.GatewayApplication"] gateway
