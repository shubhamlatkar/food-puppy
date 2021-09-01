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
	docker-compose build
	docker image prune -f
	docker-compose up