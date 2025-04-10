# Spring Boot + PostgreSQL Example

## 📌 Description

This is a simple HTTP web application built using Spring Boot and PostgreSQL. It exposes an API endpoint `/api/hello` to store and retrieve messages in the database.

## 🐳 Docker Compose Setup

### 🚀 Run

```bash
docker-compose up --build
```

- App: http://localhost:8080/api/hello
- PostgreSQL: Accessible internally

## ☸️ Kubernetes Setup with Minikube

### 🌐 Start Minikube

```bash
minikube start --driver=docker
```

### 📂 Deploy All Resources

```bash
kubectl apply -f k8s/namespace.yaml
kubectl apply -f k8s/postgres-secret.yaml
kubectl apply -f k8s/postgres-deployment.yaml
kubectl apply -f k8s/postgres-service.yaml
kubectl apply -f k8s/backend-deployment.yaml
kubectl apply -f k8s/backend-service.yaml
kubectl apply -f k8s/backend-hpa.yaml
kubectl apply -f k8s/backend-ingress.yaml
```

### 🔁 Enable Ingress

```bash
minikube addons enable ingress
minikube tunnel
```

### 🌍 Access App

Visit: [http://localhost/api/hello](http://localhost/api/hello)

## 🔒 Security Practices

- Secrets managed via Kubernetes Secrets
- Liveness and readiness probes configured (commented for local debug)
- Persistent storage for database
- Autoscaling backend pods

## ❓ Reasoning Behind

- **DB Connectivity**: Service name `postgres` is used in Spring URL via DNS.
- **Ingress**: Maps `/` path to backend app.
- **Autoscaling**: HPA scales backend on CPU usage.