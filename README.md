# security-account-api

## Endpoint criação de usuário 
- Deve-se gerar automaticamente o `clientId` que será usado posteriormente para identificar  quem está solicitando a criação de conta;
- Deve-se ocorrer validação de senha pois obrigatoriamente possuirá `caracteres e números`;
- Deve-se ocorrer a troca de senha periódica `(15 dias)`, e `talvez` avisar por e-mail quando esta troca ocorrer;
- Deve-se possuir um histórico de senha para que não se permita senhas iguais às anteriores. 


### POST /v1/users 

#### Request
```json
     {
        "email": "string",
        "password": "string",
        "nameClient": "string"
     }
```

#### Response
```json
     {
        "clientId": "string",
        "nameClient": "string",
        "email": "string"
     }
```
#### BD
```json
     {
        "clientId": "string",
        "nameClient": "string",
        "email": "string",
        "password": "string",
        "createdAt": "localDate.now()",
        "updatedAt": "localDate"
     }
```

<br/>

## Endpoint de autenticação
- Ocorrerá uma validação de usuário e senha;
     - Verificação de validade da senha, e caso esteja próxima do vencimento `D+2`, deve-se retornar uma mensagem informativa ao usuário;
     - Caso esteja vencida `D+1`, não permitir que o usuário realize autenticação.
- Token gerado terá validade de `1 minuto`;
- Campo `message` retornará informação `updatedAt` do banco de dados.


### POST /v1/auth/

#### Request
```json
     {
        "email": "string",
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
- Este endpoint depende que seja enviando os seguintes atributos no cabeçalho `clientId` e `bearerToken`;
- Ocorrerá a validação da nova senha composta por `caracteres e números`
    - Verifica se a nova senha não é condizente a senhas anteriores;
    - Atualização do campo `updatedAt` com a data atual a fins de controlar próximas alterações.

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
- Este endpoint depende que seja enviando os seguintes atributos no cabeçalho `clientId` e `bearerToken`;
- Ocorre-se a validação permitindo apenas uma conta por `document-number`;
- O valor da conta inicializará com zero, status `ATIVA`, e limites deverão ser de acordo a cada cliente;
- A conta há possibilidade de ter os seguintes status: `[ATIVA, CANCELADA, BLOQUEADA]`;
- Será gerado um número para `accountId`, e este não poderá repetir.


### POST /v1/accounts

#### Request
```json
     {
        "name": "string",
        "documentNumber": "string",
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
        "documentNumber": "string",
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
        "accountId": "string",
        "accountNumber": "string",
        "name": "string",
        "documentNumber": "string",
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
- Este endpoint depende que seja enviando os seguintes atributos no cabeçalho `clientId` e `bearerToken`;

### GET /v1/accounts/{accountNumber}

#### Response
```json
     {
        "accountNumber": "string",
        "name": "string",
        "documentNumber": "string",
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
- Toda aplicação possuirá logs com o padrão `[Class] - Text]`;
- Cobertura de testes unitários em 50%;
- Tratamento de exceções em casos de erros;
- Deve-se realizar estudos e aplicações de backups ao banco de dados.

<br/>

## Arquitetura
<br/>

![SecurityAccount](https://user-images.githubusercontent.com/31020103/158062177-ff185a68-9008-4a77-acb1-fa5baef08330.png)

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
