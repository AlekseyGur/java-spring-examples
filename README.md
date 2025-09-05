# java-spring-cloud-feign-circuit-braker

Заготовка для spring-cloud-feign-circuit-braker

Внимание! spring-cloud-starter-netflix-eureka-client уже содержит в себе spring-cloud-starter-loadbalancer, поэтому feign сам будет использовать балансировку (по умолчанию Round robin). Но если Eureka не используется, то в pom зависимости надо подключить spring-cloud-starter-loadbalancer.
