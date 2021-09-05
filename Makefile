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

start :		## Start's the application
	docker-compose build
	@echo "Build successful"
	docker image prune -f
	docker-compose up
	@echo "Application running..."

clean :		## Cleans the application and removes the docker images
	@echo "cleaning the docker images"
	docker stop $$(docker ps -a -q)
	docker rm $$(docker ps -a -q)
	docker rmi $$(docker images -q)
	@echo "clean successful"

stop :		## Stops the application
	@echo "Stopping the application"
	docker-compose down
	@echo "Application Stopped"
