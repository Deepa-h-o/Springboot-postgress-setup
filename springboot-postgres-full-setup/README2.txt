Spring Boot Application with PostgreSQL and Kubernetes Deployment
Application Overview
This Spring Boot application exposes a simple REST API with two endpoints:
- `POST /api/message`: Accepts a JSON payload containing a name, saves it to a PostgreSQL database, and responds with a personalized greeting.
- `GET /hello`: A simple health-check endpoint that returns "Hello, World!".

The application is deployed with Docker Compose for local development and Kubernetes for a production-like environment (using Minikube).
Technologies Used
- Spring Boot (Java)
- PostgreSQL
- Docker
- Kubernetes (Minikube)
- Horizontal Pod Autoscaling (HPA)
- Ingress Controller (NGINX or equivalent)
Setup and Deployment with Docker Compose
To run the application using Docker Compose:
1. Clone the repository.
2. Navigate to the project directory.
3. Run the following command to build and start the services:
   ```bash
   docker-compose up --build
   ```
4. The application will be accessible at `http://localhost:8080`.
5. The PostgreSQL database will be available at `localhost:5432` (configured in `docker-compose.yaml`).
Setup and Deployment in Kubernetes
To deploy the application in a Kubernetes environment:
1. Ensure you have a running Kubernetes cluster (e.g., Minikube).
2. Apply the required Kubernetes resources:
   ```bash
   kubectl apply -f k8s/
   ```
3. The application will be deployed in the specified namespace and will expose the web service through an Ingress (configured in `k8s/ingress.yaml`).
4. The PostgreSQL database is set up as a `StatefulSet` with persistent storage.
Configuration Details
Database Configuration in Kubernetes
The web application connects to PostgreSQL running in Kubernetes using environment variables that specify the database host, port, username, and password. The database is configured as a `StatefulSet` with a persistent volume for storage. This ensures data is retained even if the database pod is restarted.

The Spring Boot application uses the `application.properties` file to configure the JDBC URL:
```properties
spring.datasource.url=jdbc:postgresql://postgres-service:5432/mydb
spring.datasource.username=${DB_USER}
spring.datasource.password=${DB_PASSWORD}
```
The PostgreSQL service is exposed within the Kubernetes cluster via a ClusterIP service (`postgres-service`).
Exposing the Web Application in Kubernetes
The Spring Boot application is exposed externally using an Ingress resource, which routes incoming traffic to the appropriate service based on the configured hostname. The Ingress controller (e.g., NGINX) must be set up in the cluster to handle incoming requests.

Example of the Ingress configuration:
```yaml
apiVersion: networking.k8s.io/v1
kind: Ingress
metadata:
  name: web-app-ingress
  namespace: myapp
spec:
  rules:
  - host: myapp.local
    http:
      paths:
      - path: /
        pathType: Prefix
        backend:
          service:
            name: web-app-service
            port:
              number: 8080
```
Auto-scaling Configuration
The web application is configured for auto-scaling using Kubernetes Horizontal Pod Autoscaler (HPA). The HPA automatically adjusts the number of pod replicas based on CPU usage or custom metrics.

Example HPA configuration:
```yaml
apiVersion: autoscaling/v2
kind: HorizontalPodAutoscaler
metadata:
  name: web-app-hpa
  namespace: myapp
spec:
  scaleTargetRef:
    apiVersion: apps/v1
    kind: Deployment
    name: web-app-deployment
  minReplicas: 1
  maxReplicas: 5
  metrics:
  - type: Resource
    resource:
      name: cpu
      target:
        type: Utilization
        averageUtilization: 50
```
Running Locally
To run the Spring Boot application locally (without Docker or Kubernetes):
1. Clone the repository.
2. Navigate to the project directory.
3. Run the application using Maven:
   ```bash
   mvn spring-boot:run
   ```
4. The application will be accessible at `http://localhost:8080`.
Endpoints
POST /api/message
Create a new message.
Request body:
```json
{
  "name": "Deepa"
}
```
Response:
```json
{
  "id": 1,
  "name": "Deepa",
  "text": "Hello Deepa"
}
```
GET /hello
A simple health-check endpoint returning "Hello, World!".
License
This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.