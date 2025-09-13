# перед выполнением установить в систему ставим minikube
# https://minikube.sigs.k8s.io/
# командой
# curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube_latest_amd64.deb
# sudo dpkg -i minikube_latest_amd64.deb
# ставим kubectl:
# apt/snap install kubectl --classic
# ставим helm:
# snap install helm --classic

# создаём jar файлы
sh mvnw clean package

# запускаем готовую систему kubernates
minikube start
minikube addons enable ingress

# В отдельном терминале прописываем переменные окружения 
# minikube для доступа через docker команду
eval $(minikube docker-env)

kubectl cluster-info # информация о кластере

# создаём образы (внутри minikube!), в которые добавляем jar Файлы для запуска
docker build -t first-service:latest ./services/first-service
docker build -t second-service:latest ./services/second-service

# создать пространство имен
kubectl create namespace my-microservice

# готовим helm umbrella
helm dependency update helm/umbrella/.
helm lint helm/umbrella/.

# вместо my-release можно поставить любое название
helm install my-release helm/umbrella/.
helm install my-release helm/umbrella/. --dry-run --debug # режим отладки

helm list # Просмотр всех релизов
helm status my-release # проверка статуса
# helm rollback my-release <версия>  # Откат к предыдущей версии
# helm upgrade my-release helm/umbrella/. # Обновление
# helm uninstall my-release # Удаление
kubectl rollout restart deployments -n my-microservice # переустановка

# останавливаем и удаляем окружение
# minikube stop
# minikube delete