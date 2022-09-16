# belive_backend


# STACK TECNOLOGICO
* [![Java][Java]][Java-url]
* [![Spring][Spring]][Spring-url]
* [![React][React]][React-url]
* [![React Native][React Native]][React Native-url]
* [![Azure][Azure]][Azure-url]

> ## DevOps Tools & Cloud Computing - João Carlos Menk
<img align="center" alt="BeLive-HTML" height="120" width="130" src="https://github.com/samuelnovaiscavelho/img_BeLive/blob/main/Belive1.png">

# Cliente - Customer
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


# Empresa - Company

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
