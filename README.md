# spring-k8s-ingress

Заготовка для gateway на базе k8s-ingress 

Проблема - микросервисы пытаются получить имя конфиг-сервиса через дискавери сервис. Но дискавери сервис выдаёт адрес не discovery-server, а discovery-server-k8s-many-letters.