# Desafio 2: Microsserviços com Spring Boot e OpenFeign

Este projeto consiste em dois microsserviços (**Microsserviço A** e **Microsserviço B**) que se comunicam para consumir dados da API [JSONPlaceholder](https://jsonplaceholder.typicode.com). O objetivo é oferecer uma solução integrada para operações CRUD em posts e comentários.

---

## Arquitetura do Sistema
![Diagrama de Arquitetura](https://via.placeholder.com/800x400.png?text=Diagrama+de+Arquitetura+Microsserviços)

1. **Microsserviço A (Porta 8080)**:
   - Ponto de entrada da aplicação.
   - Expõe endpoints REST para o usuário final.
   - Comunica-se com o Microsserviço B via **OpenFeign**.

2. **Microsserviço B (Porta 8081)**:
   - Consome a API externa JSONPlaceholder.
   - Gerencia a lógica de negócio e persistência em banco de dados (MongoDB/H2).

---

## Pré-requisitos
- Java 17
- Maven 3.9+
- MongoDB (ou H2 para banco em memória)
- Postman/cURL (para teste dos endpoints)
- IDE (IntelliJ, Eclipse, VS Code)

---

## Instalação e Execução

### 1. Clone o Repositório
```bash
git clone https://github.com/Guerra457/Desafio2_404-Grupo-n-o-encontrado
```
### 2. Microsserviço B (Porta 8081)
```bash
cd desafio2/microsservico-b
mvn clean install
mvn spring-boot:run
```
3. Microsserviço A (Porta 8080)
```bash
cd desafio2/microsservico-a
mvn clean install
mvn spring-boot:run
```

### Configuração
#### Microsserviço A (application.properties)
```java
server.port=8080
microsservico-b.url=http://localhost:8081
```
#### Microsserviço B (application.properties)
```java
server.port=8081
# Configuração do MongoDB
spring.data.mongodb.uri=mongodb://localhost:27017/posts
# OU para H2 (banco em memória)
spring.datasource.url=jdbc:h2:mem:testdb
spring.h2.console.enabled=true
```

#### Endpoints
#### Microsserviço A (Porta 8080)
![image](https://github.com/user-attachments/assets/b2636195-a52e-48e5-9e7b-010348ad3412)

#### Microsserviço B (Porta 8081)
![image](https://github.com/user-attachments/assets/93829131-e962-43fe-9404-973d591ca51b)

### Testando os Endpoints
#### Exemplo: Criar um Post (Microsserviço A)
```bash
curl -X POST -H "Content-Type: application/json" -d '{
    "title": "Meu Post",
    "body": "Conteúdo do post",
    "userId": 1
}' http://localhost:8080/api/posts
```
#### Exemplo: Buscar Posts (Microsserviço A)
```bash
curl http://localhost:8080/api/posts
```

### Tecnologias Utilizadas
- Java 17
- Spring Boot
- Spring Cloud OpenFeign
- MongoDB / H2
- Maven
- JSONPlaceholder API


## Notas Adicionais
- Para testes unitários, execute `mvn test` em cada microsserviço.
- Use `./mvnw` (Maven Wrapper) se não tiver o Maven instalado globalmente.
- Acesse o console do H2 em `http://localhost:8081/h2-console` (JDBC URL: `jdbc:h2:mem:testdb`).
