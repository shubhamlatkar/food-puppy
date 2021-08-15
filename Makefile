NAME := foodgrid

help :
    ## Help
	@echo ""
	@echo "*** $(NAME) Makefile help ***"
	@echo ""
	@echo "Targets list:"
	@grep -E '^[a-zA-Z_-]+ :.*?## .*$$' $(MAKEFILE_LIST) | sort -k 1,1 | awk 'BEGIN {FS = ":.*?## "}; {printf "\t\033[36m%-30s\033[0m %s\n", $$1, $$2}'
    @echo ""

run :
    ## Run the application through docker
	docker build --target ui -t frontend:latest .
    docker create -ti --name frontend-container frontend bash
    docker cp  frontend-container:/frontend/delivery/build ~/foodgrid/frontend/delivery/build
    docker cp  frontend-container:/frontend/user/build ~/foodgrid/frontend/user/build
    docker cp  frontend-container:/frontend/restaurant/build ~/foodgrid/frontend/restaurant/build

    docker build --target build -t services:latest .
    docker create -ti --name services-container services bash
    docker cp  services-container:/app/delivery/target /foodgrid/delivery/target
    docker cp  services-container:/app/configuration/target /foodgrid/configuration/target
    docker cp  services-container:/app/eureka/target /foodgrid/eureka/target
    docker cp  services-container:/app/order/target /foodgrid/order/target
    docker cp  services-container:/app/accounts/target /foodgrid/accounts/target
    docker cp  services-container:/app/notification/target /foodgrid/notification/target
    docker cp  services-container:/app/user/target /foodgrid/user/target
    docker cp  services-container:/app/restaurant/target /foodgrid/restaurant/target
    docker cp  services-container:/app/gateway/target /foodgrid/gateway/target

