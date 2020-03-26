.DEFAULT_GOAL := help

env=dev
compose=docker-compose -f docker-compose.yml -f docker-compose.$(env).yml

export compose env

.PHONY: start
start: erase build up ## Clean current environment, recreate dependencies and spin up again

.PHONY: stop
stop: ## Stop environment
		$(compose) stop

.PHONY: rebuild
rebuild: start ## Same as start

.PHONY: erase
erase: ## Stop and delete containers, clean volumes.
		$(compose) stop
		docker-compose rm -v -f

.PHONY: build
build: ## Build environment
		$(compose) build

.PHONY: up
up: ## Spin up environment
		$(compose) up -d

.PHONY: ps
ps: ## Show containers list
		$(compose) ps -a

.PHONY: package
package: ## Maven package
		mvn package -Dspring.profiles.active=$(env)

.PHONY: test
test: ## Run tests
		mvn clean test -Dspring.profiles.active=$(env)

.PHONY: logs
logs: ## Look for 's' service logs, make s=auth-service logs
		$(compose) logs -f $(s)

.PHONY: client
client: ## Create client, available arguments client,secret,grants,scopes
		$(compose) exec mongo bash /create-client.sh "$(client)" "$(secret)" "$(grants)" "$(scopes)"

.PHONY: help
help: ## Display this help message
	@cat $(MAKEFILE_LIST) | grep -e "^[a-zA-Z_\-]*: *.*## *" | awk 'BEGIN {FS = ":.*?## "}; {printf "\033[36m%-30s\033[0m %s\n", $$1, $$2}'