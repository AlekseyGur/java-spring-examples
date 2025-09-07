# перед выполнением установить в систему ставим minikube
# https://minikube.sigs.k8s.io/
# командой
# curl -LO https://storage.googleapis.com/minikube/releases/latest/minikube_latest_amd64.deb
# sudo dpkg -i minikube_latest_amd64.deb
# ставим kubectl:
# apt/snap install kubectl --classic

# создаём jar файлы
sh mvnw clean package

# запускаем готовую систему kubernates
minikube start
minikube addons enable ingress

# Ui можно открыть через minikube dashboard

# В отдельном терминале прописываем переменные окружения 
# minikube для доступа через docker команду
eval $(minikube docker-env)

# создаём образы (внутри minikube!), в которые добавляем jar Файлы для запуска
docker build -t first-service:latest ./services/first-service
docker build -t second-service:latest ./services/second-service

# проверяем загруженные в minikube образы и запущенные контейнеры
# docker images
# docker ps -a

# Если не выполнять eval $(minikube docker-env), то можно скопировать
# образы в миникуб все образы так:
# minikube image load first-service:latest
# minikube image load second-service:latest

# применяем конфиги Kubernetes и запускаем контейнеры
kubectl create namespace my-microservice # создать пространство имен

# 1 важность
kubectl apply -f ./k8s/consul/ -n my-microservice

# 2 важность
kubectl apply -f ./k8s/services/first-service/ -n my-microservice
kubectl apply -f ./k8s/services/second-service/ -n my-microservice

# 3 важность
kubectl apply -f ./k8s/ingress/ -n my-microservice

# проверяем результат
# kubectl get pods -n my-microservice
# kubectl get services -n my-microservice
# kubectl get all -n my-microservice
# kubectl get networkpolicy -n my-microservice

# проверяем гейт
# kubectl describe ingress allow-config-access -n my-microservice

# пробрасываем порт от родительской машины к машине внутри кластера
# kubectl port-forward svc/consul-server 8500:8500 -n my-microservice

# включаем доступы к ингресу извне
# kubectl expose deployment web --type=NodePort --port=8080
# #смотрим какие порты открыл ingress-nginx-controller
# kubectl get svc -n ingress-nginx


# Логи ingress можно посмотреть тут:
# kubectl logs -n ingress-nginx -l app.kubernetes.io/component=controller

# Проверяем установку. Узнаём ip кластера
# minikube ip

# Удаление всех подов
# kubectl delete pods --all -n my-microservice

# Удаление всех деплойментов
# kubectl delete deployments --all -n my-microservice

# Удаление всех сервисов
# kubectl delete services --all -n my-microservice

# Удаление всех ingress
# kubectl delete ingress --all -n my-microservice

# Удаление всех statefulsets (если есть)
# kubectl delete statefulsets --all -n my-microservice

# Удаление всех jobs (если есть)
# kubectl delete jobs --all -n my-microservice

# останавливаем и удаляем окружение
# minikube stop
# minikube delete