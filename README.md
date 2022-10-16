## Belive

<img align="center" alt="BeLive-HTML" height="160" width="170" src="https://i.imgur.com/lLkrjNP.png">

## Stack tecnologico
* [![Java][Java]][Java-url] (v 17.0)
* [![Spring][Spring]][Spring-url] (v 2.7.3)
* [![React][React]][React-url] (v 16.17.0)
* [![React Native][React Native]][React Native-url] (v 16.17.0)
* [![Azure][Azure]][Azure-url] 



## Arquitetura de Solução 
<img align="center" alt="Arquitetura-Solucao" src="https://i.imgur.com/s82bYLL.jpg">

## Prototipo da aplicação (Telas)
<img align="center" alt="Arquitetura-Solucao" src="https://i.imgur.com/44myH3K.png">


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





## Arquitetura Pipeline (CI/CD)
<img align="center" alt="Arquitetura-Solucao" src="https://i.imgur.com/xH7WBiy.jpg">


### Comandos Criação da Pipeline (CI/CD)

```
-> Abrir Terminal
	- Colar o seguinte comando na VM: cat /var/lib/jenkins/secrets/initialAdminPassword
			- sudo cat /var/lib/jenkins/secrets/initialAdminPassword
			
Senha jenkis gerada:
1f3af9ba491c42b7b3650ea7b30b9f74
```
Java Utilizado
```
Versão: 11 do jdk
caminho do projeto no linux: /home/oem/IdeaProjects/belive_backend 

```
Acesso pela porta

```
http://localhost:8080

```
Credenciais da Pipelime

```
Nome de usuário: belive
Senha: grupo3
Nome completo: BeLive
Endereço de e-mail: rm88233@fiap.com.br

```

Descrição da Pipeline criada
```
O projeto BeLive tem como objetivo oferecer uma aplicação totalmente intuitiva e com uma interface amigável, com destaque para comandos de voz. 

Tais comandos são usados para acessos a todas as funcionalidades da aplicação de forma a criar um ambiente facilitador de comunicação entre tecnologia e usuário, a usabilidade da aplicação é focada para indivíduos que tem dificuldade com tecnologia, então pensamos em uma forma dinâmica e facilitadora na questão de usabilidade.

```

Instalação do plugin no Jenkins

```
Blue Ocean

```

Criação da Pipeline

```
node {

  def resourceGroupName = 'rg-belive'
  def resourceGroupLocation = 'brazilsouth'
  def appServicePlanName = 'belivePlan'
  def appServicePlanTier = 'FREE'
  def webAppName = 'belive-rm88233'
  def webAppRuntime = '"java:11:Java SE:11"'
  def packagePath = 'target/belive_backend-0.0.1-SNAPSHOT.jar'

  stage('Extrair Codigo Fonte') {
    echo 'Obtendo o Código Fonte ...'
    checkout([$class: 'GitSCM', branches: [[name: '*/master']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], 
userRemoteConfigs: [[url: 'https://github.com/samuelnovaiscavelho/belive_backend.git']]])
  }

  stage('Build') {
    echo 'Empacotando o projeto...'
    sh '/opt/maven/bin/mvn clean package'
  }

  stage('Credenciais Azure') {
    echo 'Obtendo credenciais...'
    withCredentials([usernamePassword(credentialsId: 'AzureService', 
      passwordVariable: 'AZURE_CLIENT_SECRET',
      usernameVariable: 'AZURE_CLIENT_ID')]) {
      echo 'Logando na Azure...'
      sh 'az login -u $AZURE_CLIENT_ID -p $AZURE_CLIENT_SECRET'
    }
  }

  stage('Criar Infra') {
    echo 'Criando o Grupo de Recursos...'
    sh "az group create --name $resourceGroupName --location $resourceGroupLocation"
    echo 'Criando Plano de Serviço...'
    sh "az appservice plan create --name $appServicePlanName --resource-group $resourceGroupName --sku $appServicePlanTier"
    echo 'Criando o Web App...'
    sh "az webapp create --name $webAppName --plan $appServicePlanName --resource-group $resourceGroupName --runtime $webAppRuntime"
  }

  stage('Deploy') {
     echo 'Realizando o Deploy na Azure...'
     sh "az webapp deploy --resource-group $resourceGroupName --name $webAppName --src-path $packagePath --type jar"
  }

}

```
Iniciar Web Browser com Aplicação

```
az login

az webapp browse --name belive-rm88233 --resource-group rg-belive

```
Listar Runtimes

```
http://localhost:8080

```







