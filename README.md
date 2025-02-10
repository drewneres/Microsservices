# Desafio 2: Microsserviços com Spring Boot e OpenFeign

Este projeto consiste em dois microsserviços (**Microsserviço A** e **Microsserviço B**) que se comunicam para consumir dados da API [JSONPlaceholder](https://jsonplaceholder.typicode.com). O objetivo é oferecer uma solução integrada para operações CRUD em posts e comentários.

---

## Arquitetura do Sistema

1. **Microsserviço A (Porta 8080)**:
   - Ponto de entrada da aplicação.
   - Expõe endpoints REST para o usuário final.
   - Comunica-se com o Microsserviço B via **OpenFeign**.

2. **Microsserviço B (Porta 8081)**:
   - Consome a API externa JSONPlaceholder.
   - Gerencia a lógica de negócio e persistência em banco de dados (H2).

---

## Pré-requisitos

- Java 17
- Maven 3.9+
- H2 DB
- Postman/cURL (para teste dos endpoints)
- IDE (IntelliJ, Eclipse, VS Code).
  - Recomendado: IntelliJ.

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

### 3. Microsserviço A (Porta 8080)
```bash
cd desafio2/microsservico-a
mvn clean install
mvn spring-boot:run
```
### Executando pela IDE  
- Você também pode rodar a aplicação diretamente pela IDE. Localize na main os seguintes arquivos:
-    No **Micro B**, localize e execute a classe **`MicrosservicoBApplication`**.  
-    No **Micro A**, localize e execute a classe **`MicrosservicoAApplication`**.  

>  **Importante:** Para o funcionamento correto da aplicação, ambos os microsserviços devem estar em execução simultaneamente. **Recomendação:** Execute primeiro o Microsserviço B e, em seguida, o Microsserviço A.  
> **Dica:** Antes de iniciar os microsserviços, verifique se todas as dependências estão configuradas corretamente.

---

## Configuração

### Microsserviço A (application.properties)
```properties
server.port=8080
microsservico-b.url=http://localhost:8081
```

### Microsserviço B (application.properties)
```properties
spring.application.name=Desafio2
server.port=8081

spring.datasource.driverClassName=org.h2.Driver
spring.datasource.url=jdbc:h2:mem:micro2
spring.datasource.username=grupo4
spring.datasource.password=
spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
management.tracing.enabled=false

spring.h2.console.enabled=true
spring.h2.console.path=/micro2-console

springdoc.swagger-ui.path=/docs-micro2.html
springdoc.api-docs.path=/docs-micro2
springdoc.packagesToScan=org.compass.desafio2.controller
```

### Acesso ao Banco de Dados H2  

Para visualizar os dados armazenados no banco de dados H2, acesse o console pelo seguinte endereço:  

 - URL: [http://localhost:8081/micro2-console](http://localhost:8081/micro2-console)
 - JDBC URL: `jdbc:h2:mem:micro2-console`  

### Credenciais de Acesso  
- Usuário: `grupo4`  
- Senha: *(deixe em branco)*  

>  **Observação:** Certifique-se de que a aplicação está em execução para acessar o banco de dados.

---

## Endpoints

### Microsserviço A (Porta 8080)
![image](https://github.com/user-attachments/assets/b2636195-a52e-48e5-9e7b-010348ad3412)

### Microsserviço B (Porta 8081)
![image](https://github.com/user-attachments/assets/93829131-e962-43fe-9404-973d591ca51b)

---

## Testando os Endpoints

### Sincronizando Dados no Microsserviço B

- Como o **Microsserviço A** depende do **Microsserviço B**, é fundamental rodar a sincronização de dados no **Microsserviço B** para garantir que as informações sejam corretamente persistidas no banco de dados.

#### Sincronização Completa
- Se você deseja sincronizar todos os dados da API e enviá-los para o banco de dados de uma vez, utilize o seguinte endpoint:
  - **Método:** `POST`
  - **URL:** `http://localhost:8080/api/sync-data`

#### Sincronização Individual
- Caso prefira sincronizar os dados separadamente, você pode utilizar os seguintes endpoints:

  - **Sincronizar Posts da API e enviar para o BD:**
    - **Método:** `POST`
    - **URL:** `http://localhost:8080/api/sync/posts`

  - **Sincronizar Comments da API e enviar para o BD:**
    - **Método:** `POST`
    - **URL:** `http://localhost:8080/api/sync/comments`

  - **Sincronizar Users da API e enviar para o BD:**
    - **Método:** `POST`
    - **URL:** `http://localhost:8080/api/sync/users`

---

### Exemplo: Criar um Post (Microsserviço A)

#### Via Postman:
- **Método:** `POST`
- **URL:** `http://localhost:8080/api/posts`
- **Headers:**
  ```json
  {
      "Content-Type": "application/json"
  }
  ```
- **Body (Raw - JSON):**
  ```json
  {
      "id": 1,
      "title": "Meu Post",
      "body": "Conteúdo do post",
      "userId": 1
  }
  ```

---

### Exemplo: Buscar Posts (Microsserviço A)

#### Via Postman:
- **Método:** `GET`
- **URL:** `http://localhost:8080/api/posts`

---

### Exemplo: Buscar um Post Específico (Microsserviço A)

#### Via Postman:
- **Método:** `GET`
- **URL:** `http://localhost:8080/api/posts/1`

---

### Exemplo: Atualizar um Post (Microsserviço A)

#### Via Postman:
- **Método:** `PUT`
- **URL:** `http://localhost:8080/api/posts/1`
- **Headers:**
  ```json
  {
      "Content-Type": "application/json"
  }
  ```
- **Body (Raw - JSON):**
  ```json
  {
      "id": 1,
      "title": "Meu Post Atualizado",
      "body": "Conteúdo atualizado do post",
      "userId": 1
  }
  ```

---

### Exemplo: Deletar um Post (Microsserviço A)

#### Via Postman:
- **Método:** `DELETE`
- **URL:** `http://localhost:8080/api/posts/1`

---

## Documentação da API  - Swagger

- O projeto inclui a documentação da API utilizando **Swagger**, proporcionando uma interface interativa para explorar os endpoints disponíveis.  
- **A documentação pode ser acessada pelo link:** [http://localhost:8081/docs-micro2.html](http://localhost:8081/docs-micro2.html)

---
## Testes

- Durante a fase de testes, foram validados diversos cenários para garantir a  confiabilidade da aplicação. 
- Os testes foram implementados na pasta `tests/java/org.compass.desafio2` de ambos os repositórios do Micro A e MicroB.
- Os principais status HTTP testados e confirmados incluem:
  - 200 (OK): Indica que a requisição foi bem-sucedida. Utilizado para operações de consulta, como buscar todos os posts ou um post específico.
  - 201 (Created): Retornado quando um novo recurso é criado com sucesso, como a criação de um novo post.
  - 204 (No Content): Resposta para operações de atualização bem-sucedidas.
  - 400 (Bad Request): Utilizado para indicar que a requisição foi malformada ou contém dados inválidos, como um JSON incompleto ou incorreto.
  - 404 (Not Found): Retornado quando o recurso solicitado não existe, como tentar buscar um post com um ID inexistente. (Obs: Esse status não poderia faltar, já que ele leva o nome do nosso grupo!)

---
## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Cloud OpenFeign
- H2
- Maven
- JSONPlaceholder API

---

## Notas Adicionais

- O console do H2 está disponível em `http://localhost:8081/micro2-console` (JDBC URL: `jdbc:h2:mem:micro2-console`).
   - Credenciais:
      - Username: `grupo4`
      - Password: (vazio)
