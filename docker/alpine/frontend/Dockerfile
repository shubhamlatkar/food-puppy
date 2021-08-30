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
