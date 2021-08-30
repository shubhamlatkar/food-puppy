# Makefile
# see https://www.gnu.org/software/make/manual/make.html

# VARIABLES

NAME := FOODGRID

.PHONY: help
.DEFAULT_GOAL := help

# GENERAL
help :		## Help
	@echo ""
	@echo "*** $(NAME) Makefile help ***"
	@echo ""
	@echo "Targets list:"
	@grep -E '^[a-zA-Z_-]+ :.*?## .*$$' $(MAKEFILE_LIST) | sort -k 1,1 | awk 'BEGIN {FS = ":.*?## "}; {printf "\t\033[36m%-30s\033[0m %s\n", $$1, $$2}'
	@echo ""

build :		## Build the application
	docker build -f ~/foodgrid/docker/alpine/frontend/Dockerfile --target ui -t frontend:latest .
	docker create -ti --name frontend frontend bash
	docker cp frontend:/frontend/delivery/build/ ~/foodgrid/delivery/src/main/resources/static
	docker cp  frontend:/frontend/user/ ~/foodgrid/user/src/main/frontend/build
	docker cp  frontend:/frontend/restaurant/ ~/foodgrid/restaurant/src/main/frontend/build

	docker stop $$(docker ps -a -q)
	docker rm $$(docker ps -a -q)
	docker rmi $$(docker images -q)

	docker build -f ~/foodgrid/docker/alpine/backend/Dockerfile --target build -t services:latest .
	docker create -ti --name services services bash
	docker cp  services:/app/delivery/target ~/foodgrid/delivery/target
	docker cp  services:/app/configuration/target ~/foodgrid/configuration/target
	docker cp  services:/app/eureka/target ~/foodgrid/eureka/target
	docker cp  services:/app/order/target ~/foodgrid/order/target
	docker cp  services:/app/accounts/target ~/foodgrid/accounts/target
	docker cp  services:/app/notification/target ~/foodgrid/notification/target
	docker cp  services:/app/user/target ~/foodgrid/user/target
	docker cp  services:/app/restaurant/target ~/foodgrid/restaurant/target
	docker cp  services:/app/gateway/target ~/foodgrid/gateway/target

	docker stop $$(docker ps -a -q)
	docker rm $$(docker ps -a -q)
	docker rmi $$(docker images -q)