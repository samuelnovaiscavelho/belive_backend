## Belive

<img align="center" alt="BeLive-HTML" height="120" width="130" src="https://github.com/samuelnovaiscavelho/img_BeLive/blob/main/Belive1.png">

## Stack tecnologico
* [![Java][Java]][Java-url] (v 17.0)
* [![Spring][Spring]][Spring-url] (v 2.7.3)
* [![React][React]][React-url] (v 16.17.0)
* [![React Native][React Native]][React Native-url] (v 16.17.0)
* [![Azure][Azure]][Azure-url] 



## Arquitetura de Solução 
<img align="center" alt="Arquitetura-Solucao" src="https://i.imgur.com/6erHJsM.png">

## Cliente - Customer
### POST REGISTER

[POST] - http://localhost:8080/user/customer/register
```
{
	"name": "Everton",
	"address": {
		"street": "Rua das Ruas",
		"district": "Bairro",
		"city": "Cidade",
		"state": "Estado",
		"zipCode": "00000-000"
	},
	"userLogin" : {
		"username" : "cliente@email.com",
		"password" : "colocarSenha"
	},
	"cpf": "000.000.000-00"
}
```

### POST LOGIN

[POST] - http://localhost:8080/user/customer/login
```
{
	"username" : "cliente@email.com",
	"password" : "colocarSenha"
}
```

### GET CUSTOMER

[GET] - http://localhost:8080/user/customer/get

Authorization -> **Type:** Basic Auth

```diff
- (* required)
```
- [x] Username
- [x] Password


### PATCH UPDATE CUSTOMER

[PATCH] - http://localhost:8080/user/customer/update
```
{
	"name": "nomeCliente"
}
```
Authorization -> **Type:** Basic Auth

```diff
- (* required)
```
- [x] Username
- [x] Password

### DELETE CUSTOMER

[DELETE] - http://localhost:8080/user/customer/delete

Authorization -> **Type:** Basic Auth

```diff
- (* required)
```
- [x] Username
- [x] Password


## Empresa - Company

### POST REGISTER

[POST] - http://localhost:8080/user/company/register
```
{
	"name": "Empresa do Everton",
	"address": {
		"street": "Rua das Ruas",
		"district": "Bairro",
		"city": "Cidade",
		"state": "Estado",
		"zipCode": "00000-000"
	},
	"userLogin" : {
		"username" : "empresa@email.com",
		"password" : "colocarSenha"
	},
	"cnpj": "45.891.557/0001-06"
}

```
### POST LOGIN

[POST] - http://localhost:8080/user/company/login

```
{
	"username" : "empresa@email.com",
	"password" : "colocarSenha"
}
```

### GET CUSTOMER

[GET] - http://localhost:8080/user/company/get

Authorization -> **Type:** Basic Auth

```diff
- (* required)
```
- [x] Username
- [x] Password


### PATCH UPDATE CUSTOMER

[PATCH] - http://localhost:8080/user/company/update
```
{
	"name": "Empresa do Everton Atualizada 2"
}
```
Authorization -> **Type:** Basic Auth

```diff
- (* required)
```
- [x] Username
- [x] Password

### DELETE CUSTOMER

[DELETE] - http://localhost:8080/user/company/delete

Authorization -> **Type:** Basic Auth

```diff
- (* required)
```
- [x] Username
- [x] Password


## DDL
```
CREATE TABLE tb_address (
    id_address   NUMBER(9) NOT NULL,
    nm_street    VARCHAR2(100) NOT NULL,
    nm_district  VARCHAR2(50) NOT NULL,
    nm_city      VARCHAR2(50) NOT NULL,
    nm_state     VARCHAR2(20) NOT NULL,
    cd_zipcode   VARCHAR2(9) NOT NULL
);

ALTER TABLE tb_address ADD CONSTRAINT tb_address_pk PRIMARY KEY ( id_address );

CREATE TABLE tb_cliente (
    tb_user_id_usuario  NUMBER(9) NOT NULL,
    cpf                 VARCHAR2(15) NOT NULL
);

ALTER TABLE tb_cliente ADD CONSTRAINT tb_cliente_pk PRIMARY KEY ( tb_user_id_usuario );

CREATE TABLE tb_company (
    tb_user_id_usuario  NUMBER(9) NOT NULL,
    cnpj                VARCHAR2(20) NOT NULL
);

ALTER TABLE tb_company ADD CONSTRAINT tb_company_pk PRIMARY KEY ( tb_user_id_usuario );

CREATE TABLE tb_user (
    id_usuario                   NUMBER(9) NOT NULL,
    nm_usuario                   VARCHAR2(256) NOT NULL,
    type_user                    VARCHAR2(10) NOT NULL,
    tb_user_login_id_user_login  NUMBER(9) NOT NULL,
    tb_address_id_address        NUMBER(9) NOT NULL
);

ALTER TABLE tb_user ADD CONSTRAINT tb_user_pk PRIMARY KEY ( id_usuario );

CREATE TABLE tb_user_login (
    id_user_login   NUMBER(9) NOT NULL,
    login_email     VARCHAR2(256) NOT NULL,
    login_password  VARCHAR2(20) NOT NULL
);

ALTER TABLE tb_user_login ADD CONSTRAINT tb_user_login_pk PRIMARY KEY ( id_user_login );

ALTER TABLE tb_cliente
    ADD CONSTRAINT tb_cliente_tb_user_fk FOREIGN KEY ( tb_user_id_usuario )
        REFERENCES tb_user ( id_usuario );

ALTER TABLE tb_company
    ADD CONSTRAINT tb_company_tb_user_fk FOREIGN KEY ( tb_user_id_usuario )
        REFERENCES tb_user ( id_usuario );

ALTER TABLE tb_user
    ADD CONSTRAINT tb_user_tb_address_fk FOREIGN KEY ( tb_address_id_address )
        REFERENCES tb_address ( id_address );

ALTER TABLE tb_user
    ADD CONSTRAINT tb_user_tb_user_login_fk FOREIGN KEY ( tb_user_login_id_user_login )
        REFERENCES tb_user_login ( id_user_login );

```

<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[Java]: https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white
[Java-url]: https://www.java.com/pt-BR/download/
[React]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Spring]: https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white
[Spring-url]: https://spring.io/projects/spring-framework
[React Native]: https://img.shields.io/badge/react_native-%2320232a.svg?style=for-the-badge&logo=react&logoColor=%2361DAFB
[React Native-url]: https://reactnative.dev/
[Azure]: https://img.shields.io/badge/azure-%230072C6.svg?style=for-the-badge&logo=microsoftazure&logoColor=white
[Azure-url]: https://azure.microsoft.com/pt-br/
