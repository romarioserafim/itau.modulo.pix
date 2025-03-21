# itau.modulo.pix


# API de Cadastro de Pix

## 📌 Sobre
Esta API permite o cadastro e gerenciamento de chaves Pix.

## 🚀 Tecnologias
- **Java 23** (JDK 23)
- **Spring Boot**
- **Maven** (Gerenciador de dependências)
- **Docker & Docker Compose**
- **MySQL** (Banco de dados)

## 🛠 Pré-requisitos
Antes de iniciar, certifique-se de ter instalado:
- [JDK 23](https://jdk.java.net/23/)
- [Docker](https://www.docker.com/)
- [Docker Compose](https://docs.docker.com/compose/install/)
- [Maven](https://maven.apache.org/)

## 🚀 Como subir o projeto
1. Clone o repositório:
   ```sh
   git clone https://github.com/romarioserafim/itau.modulo.pix.git
   cd itau.modulo.pix.git
   ```
2. Suba o banco de dados MySQL com Docker Compose:
   ```sh
   docker-compose up -d
   ```
3. Compile e execute a aplicação via Maven:
   ```sh
   mvn spring-boot:run
   ```

## 🗄 Estrutura do projeto
```
├── src/main/java/com/modulo.pix
│   ├── controllers/    # Controladores da API
│   ├── services/       # Serviços de negócio
│   ├── repositories/   # Repositórios para acesso ao banco
│   ├── models/         # Modelos de entidade
│   ├── dto/            # Data Transfer Objects
│   ├── enums/          # Enums
│   ├── exception/      # exception personalizados
│   ├── utils/          # Classe/funcao e apoio
│   ├── validation/     # validações personalizadas
│
├── src/main/resources/
│   ├── application.properties  # Configuração do sistema
│
├── docker-compose.yaml  # Configuração do banco MySQL
├── pom.xml  # Dependências do projeto
```

## 🏗 Aplicação dos princípios Twelve-Factor
### 📌 Repositório único (*Codebase*)
A aplicação possui um único repositório de código versionado no GitHub, garantindo consistência no desenvolvimento e facilitando a colaboração.

### 📌 Gerenciador de dependências globais (*Dependencies*)
O projeto utiliza **Maven** para gerenciar dependências de forma centralizada, evitando a necessidade de pacotes incorporados diretamente no código-fonte.

### 📌 Serviços de apoio (*Backing Services*)
A API depende de um banco de dados MySQL que é tratado como um serviço externo, gerenciado via **Docker Compose**, facilitando a escalabilidade e a configuração.

### 📌 Base de dados separada (*Backing Services - Database*)
O banco de dados MySQL não está embutido na aplicação, permitindo que seja executado separadamente como um serviço gerenciado pelo Docker, seguindo o princípio de desacoplamento.
