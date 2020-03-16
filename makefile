env=dev
compose=docker-compose -f docker-compose.yml -f docker-compose.$(env).yml

export compose env

.PHONY: start
start: erase build up ## clean current environment, recreate dependencies and spin up again

.PHONY: stop
stop: ## stop environment
		$(compose) stop

.PHONY: rebuild
rebuild: start ## same as start

.PHONY: erase
erase: ## stop and delete containers, clean volumes.
		$(compose) stop
		docker-compose rm -v -f

.PHONY: build
build: ## build environment and initialize composer and project dependencies
		$(compose) build

.PHONY: up
up: ## spin up environment
		$(compose) up -d

.PHONY: package
package: ## maven pacakge
		mvn package -Dspring.profiles.active=$(env)

.PHONY: logs
logs: ## look for 's' service logs, make s=auth-service logs
		$(compose) logs -f $(s)

.PHONY: help
help: ## Display this help message
	@cat $(MAKEFILE_LIST) | grep -e "^[a-zA-Z_\-]*: *.*## *" | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'