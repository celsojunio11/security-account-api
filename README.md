# security-account-api

## Endpoint criação de usuário 

- Deve ser gerado automaticamente um `client-id` que será usado para posteriormente 
     identificar quem está solicitando a criação de conta;
- Deve ocorrer uma validação de senha pois deve possui `caracteres e números`;
- Deve ocorrer troca de senha periódica `(15 dias)` e `talvez` avisar por e-mail quando ocorrer a troca de senha;
- Deve possuir histórico de senha para não permitir a senha seja igual às anteriores. 

### POST /v1/users 

#### Request
```json
     {
        "user": "string",
        "password": "string",
        "nameClient": "string"
     }
```

#### Response
```json
     {
        "nameClient": "string",
        "clientId": "uuid",
        "user": "string",
        "password": "string"
     }
```
#### BD
```json
     {
        "clientId": "uuid",
        "nameClient": "string",
        "user": "string",
        "password": "string",
        "createdAt": "localDate.now()",
        "updatedAt": "localDate.now()"
     }
```

<br/>

## Endpoint de autenticação 

- Este endpoint depende do cabeçalho `client-id` para identificar qual cliente está enviando a requisição;
- Deve ocorrer uma validação de usuário e senha;
    - Verificar a validade da senha, caso esteja próximo do vencimento `D+2` retornar uma mensagem avisando o usuário;
    - Caso esteja vencida `D+1` não permitir que o usuário realize autenticação.
- Token gerado deve ter validade de `1 minuto`;
- Campo `message` deve retornar informação `updatedAt` do banco de dados;

### POST /v1/auth/{clientId}

#### Request
```json
     {
        "user": "string",
        "password": "string"
     }
```
#### Response
```json
     {
        "bearerToken": "string",
        "message": "string"
     }
```
<br/>

## Endpoint de alteração de senha

- Este endpoint depende do cabeçalho `client-id` e `bearer-token`;
- Deve ocorrer validação do nova senha de `caracteres e números`
    - Deve verificar se nova senha não é igual a senhas anteriores;
    - Deve atualizar o campo `updatedAt` com a data atual para controle das próximas alterações.

### PATCH /v1/users

#### Request
```json
     {
        "user": "string",
        "currentPassword": "string",
        "newPassword": "string"
     }
```

<br/>

## Endpoint criação de conta
- Este endpoint depende do cabeçalho `client-id` e `bearer-token`;
- Deve ocorrer uma validação permitindo apenas uma conta por `document-number`;
- Valor da conta deve ser iniciado com zero, status `ATIVA`, limites deve ser de acordo com cada cliente;
- Conta pode ter os seguintes status: `[ATIVA, CANCELADA, BLOQUEADA]`;
- Deve ser gerado um numero para `accountId` lembrando que o mesmo não pode repetir.

### POST /v1/accounts

#### Request
```json
     {
        "name": "string",
        "document_number": "string",
        "email": "string",
        "phone": "string",
        "address": {
            "street": "string",
            "number": 0,
            "district": "string",
            "city": "string",
            "state": "string",
            "complement": "string",
            "postalCode": "string",
        }
     }
```

#### Response
```json
     {
        "accountId": "string",
        "name": "string",
        "document_number": "string",
        "email": "string",
        "status": "ATIVA",
        "amount": 0.0,
        "limits": {
            "pix": 0.0,
            "ted": 0.0,
            "ticket": 0.0
        }
     }
```

#### BD
```json
     {
        "accountId": "uuid",
        "accountNumber": "string",
        "name": "string",
        "document_number": "string",
        "email": "string",
        "address": {
            "street": "string",
            "number": 0,
            "district": "string",
            "city": "string",
            "state": "string",
            "complement": "string",
            "postalCode": "string",
        },
        "clientId": "string",
        "status": "ATIVA",
        "amount": 0.0,
        "limits": {
            "pix": 0.0,
            "ted": 0.0,
            "ticket": 0.0
        },
        "createdAt": "localDate.now()",
        "updatedAt": "localDate.now()"
     }
```
<br/>

## Endpoint listar conta
- Este endpoint depende do cabeçalho `client-id` e `bearer-token`;

### GET /v1/accounts/{accountNumber}

#### Response
```json
     {
        "accountNumber": "string",
        "name": "string",
        "document_number": "string",
        "email": "string",
        "address": {
            "street": "string",
            "number": 0,
            "district": "string",
            "city": "string",
            "state": "string",
            "complement": "string",
            "postalCode": "string",
        },
        "status": "ATIVA",
        "amount": 0.0,
        "limits": {
            "pix": 0.0,
            "ted": 0.0,
            "ticket": 0.0
        }
     }
```

## Regras da aplicação
- Toda aplicação deve possui logs com o padrão `[Class] - Text]`;
- Cobertura de testes unitários em 50%;
- Tratamento de exceções em casos de erros;
- Deve ser realizado estudos e aplicação de backup do banco de dados;

<br/>

## Arquitetura
<br/>

![SecurityAccount](https://user-images.githubusercontent.com/31020103/156883695-09b26b69-a708-4147-a659-aa1098893e36.png)

<br/>

#### Principais bibiliotecas utilizadas

* [Spring Web](https://mvnrepository.com/artifact/org.springframework/spring-web)
* [Spring Boot Starter Data JPA](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-starter-data-jpa)
* [Spring Boot DevTools](https://mvnrepository.com/artifact/org.springframework.boot/spring-boot-devtools)
* [Hibernate Validator](https://hibernate.org/validator/)
* [Loombok](https://mvnrepository.com/artifact/org.projectlombok/lombok)
* [Swagger](https://swagger.io/)
* [MySQL Connector/J](https://mvnrepository.com/artifact/mysql/mysql-connector-java)
* [JWT](https://jwt.io/introduction)
* [Docker](https://docs.docker.com/)
* [UUID](https://docs.oracle.com/javase/7/docs/api/java/util/UUID.html)

#### AUTORES

- [Celso Junior](https://www.linkedin.com/in/celso-junio-003a55180/)
- [Elias Borges](https://www.linkedin.com/in/eliasborges)
