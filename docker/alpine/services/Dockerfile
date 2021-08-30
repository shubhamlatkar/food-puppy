#### Stage 1: A  docker image with command to run the configuration
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as configuration

# Copy project dependencies from the build stage
COPY ../../configuration/target/dependency/BOOT-INF/lib /app/lib
COPY ../../configuration/target/dependency/META-INF /app/META-INF
COPY ../../configuration/target/dependency/BOOT-INF/classes /app/

EXPOSE 8888
ENTRYPOINT ["/bin/sh", "-c", "sleep 30 && java -cp app:app/lib/* com.foodgrid.configuration.ConfigurationApplication"] configuration

#### Stage 2: A  docker image with command to run the eureka
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as eureka

# Copy project dependencies from the build stage
COPY ../../eureka/target/dependency/BOOT-INF/lib /app/lib
COPY ../../eureka/target/dependency/META-INF /app/META-INF
COPY ../../eureka/target/dependency/BOOT-INF/classes /app/

EXPOSE 8761
ENTRYPOINT ["/bin/sh", "-c", "sleep 50 && java -cp app:app/lib/* com.foodgrid.eureka.EurekaApplication"] eureka

#### Stage 3: A docker image with command to run the order
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as order

# Copy project dependencies from the build stage
COPY ../../order/target/dependency/BOOT-INF/lib /app/lib
COPY ../../order/target/dependency/META-INF /app/META-INF
COPY ../../order/target/dependency/BOOT-INF/classes /app/

EXPOSE 8084
ENTRYPOINT ["/bin/sh","-c", "sleep 70 && java -cp app:app/lib/* com.foodgrid.order.OrderApplication"] order

#### Stage 4: A docker image with command to run the accounts
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as accounts

# Copy project dependencies from the build stage
COPY ../../accounts/target/dependency/BOOT-INF/lib /app/lib
COPY ../../accounts/target/dependency/META-INF /app/META-INF
COPY ../../accounts/target/dependency/BOOT-INF/classes /app/

EXPOSE 8086
ENTRYPOINT ["/bin/sh","-c", "sleep 80 && java -cp app:app/lib/* com.foodgrid.accounts.AccountsApplication"] accounts

#### Stage 5: A docker image with command to run the notification
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as notification

# Copy project dependencies from the build stage
COPY ../../notification/target/dependency/BOOT-INF/lib /app/lib
COPY ../../notification/target/dependency/META-INF /app/META-INF
COPY ../../notification/target/dependency/BOOT-INF/classes /app/

EXPOSE 8083
ENTRYPOINT ["/bin/sh","-c", "sleep 90 && java -cp app:app/lib/* com.foodgrid.notification.NotificationApplication"] notification

#### Stage 6: A  docker image with command to run the user
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as user

# Copy project dependencies from the build stage
COPY ../../../user/target/dependency/BOOT-INF/lib /app/lib
COPY ../../../user/target/dependency/META-INF /app/META-INF
COPY ../../../user/target/dependency/BOOT-INF/classes /app/

EXPOSE 8081
# ENTRYPOINT ["java","-cp","app:app/lib/*","com.foodgrid.user.UserServiceApplication"] user
# "/bin/sh", "-c", "sleep 10 && java -cp app:app/lib/* com.foodgrid.user.UserServiceApplication"
ENTRYPOINT ["/bin/sh", "-c", "sleep 120 && java -cp app:app/lib/* com.foodgrid.user.UserApplication"] user


#### Stage 7: A docker image with command to run the restaurant
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as restaurant

# Copy project dependencies from the build stage
COPY ../../../restaurant/target/dependency/BOOT-INF/lib /app/lib
COPY ../../../restaurant/target/dependency/META-INF /app/META-INF
COPY ../../../restaurant/target/dependency/BOOT-INF/classes /app/

EXPOSE 8082
ENTRYPOINT ["/bin/sh","-c", "sleep 130 && java -cp app:app/lib/* com.foodgrid.restaurant.RestaurantApplication"] restaurant

#### Stage 8: A docker image with command to run the delivery
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as delivery

# Copy project dependencies from the build stage
COPY ../../../delivery/target/dependency/BOOT-INF/lib /app/lib
COPY ../../../delivery/target/dependency/META-INF /app/META-INF
COPY ../../../delivery/target/dependency/BOOT-INF/classes /app/

EXPOSE 8085
ENTRYPOINT ["/bin/sh","-c", "sleep 140 && java -cp app:app/lib/* com.foodgrid.delivery.DeliveryApplication"] delivery

#### Stage 9: A  docker image with command to run the gateway
FROM mcr.microsoft.com/java/jre-headless:11-zulu-alpine as gateway

# Copy project dependencies from the build stage
COPY ../../../gateway/target/dependency/BOOT-INF/lib /app/lib
COPY ../../../gateway/target/dependency/META-INF /app/META-INF
COPY ../../../gateway/target/dependency/BOOT-INF/classes /app/

EXPOSE 8080
ENTRYPOINT ["/bin/sh", "-c", "sleep 160 && java -cp app:app/lib/* com.foodgrid.gateway.GatewayApplication"] gateway