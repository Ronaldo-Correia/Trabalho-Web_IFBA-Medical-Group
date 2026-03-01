# 🏥 Sistema Clínica - Trabalho WEB

Este projeto é um sistema web para gerenciamento de uma clínica médica, desenvolvido como parte de um trabalho da disciplina de Desenvolvimento Web.
O sistema permite o cadastro de pacientes e profissionais da saúde, além de agendamento e visualização de consultas.

---

## 🔧 Tecnologias Utilizadas

- Java 17/21
- Spring Boot
- JTE (Java Template Engine)
- HTML/CSS
- Banco de dados H2/PostgreSQL

---

## 📝 Funcionalidades

- Cadastro e listagem de pacientes
- Cadastro e listagem de profissionais
- Agendamento e listagem de consultas
- Validações de dados nos formulários
- Exibição de dados organizados em uma interface simples

---

## 👨‍💻 Como executar o sistema localmente

- 1: Substitua o datasource no arquivo `application.yaml` por:

```
  datasource:
    url: jdbc:h2:mem:testdb
    username: sa
    password:
    driver-class-name: org.h2.Driver
```

- 2: No terminal da sua IDE execute esse comando:

```
mvn clean install spring-boot:run
```

- 3: Acesse o site local
  [IFBA Medical Group](http://localhost:8081/)

---

## 🐳 Executando com Docker

O projeto já possui:

Dockerfile (build multi-stage com Maven)

docker-compose.yml (app + PostgreSQL)

Não é necessário instalar Java ou PostgreSQL na máquina. Só precisa ter Docker Desktop instalado.

### 1. Subindo a aplicação

```bash
# a partir da raiz do projeto
cd raiz-do-projeto```

### 2. Executando com docker run

```bash
docker compose up --build

```

O serviço `clinic-app` será iniciado na porta 8082 e o banco PostgreSQL será provisionado.
As variáveis de conexão podem ser configuradas no `docker-compose.yml` ou via `environment`.

O arquivo `.dockerignore` já elimina artefatos de build e IDE.

> 🔧 **Dica:** se preferir usar o H2 em memória, comente ou remova as variáveis `SPRING_DATASOURCE_*` no compose.

---

## 🚀 Como usar (navegação)

Depois de iniciar a aplicação, abra o navegador em `http://localhost:8082/login` (ou `:8081` se estiver executando sem Docker). Utilize as credenciais padrão criadas no `init.sql`:

- **Email:** `admin@clinic.com`
- **Senha:** `admin123`

A partir da tela inicial você pode acessar o menu para:

1. Cadastrar e listar **pacientes**.
2. Cadastrar e listar **profissionais**.
3. Marcar, visualizar e cancelar **agendamentos**.
4. Utilizar o link **Home** para retornar à página principal.

Os formulários exibem mensagens de erro quando a validação falha; ao salvar, os dados são persistidos no banco configurado (H2 em memória ou PostgreSQL).

> Para testes rápidos sem login, comente as regras de segurança em `SecurityConfig`.

---

## 🏗 Arquitetura do Sistema

O sistema Trabalho-Web_IFBA-Medical-Group foi desenvolvido em Java (Spring Boot) seguindo uma arquitetura em camadas, que separa responsabilidades de forma clara:

### 📂 Camadas da Aplicação:

- Config (config) Contém classes de configuração do sistema, como segurança, inicialização de dados e interceptadores.
- Constantes (consts) Centraliza constantes utilizadas em rotas e requisições.
- Controller (controller) Responsável por receber as requisições HTTP, chamar os serviços adequados e retornar respostas (MVC – camada de apresentação).
- DTO (dto) Objetos de transferência de dados entre as camadas, garantindo isolamento entre entidades e a API.
- Entity (entity) Representa as entidades do domínio e mapeamento para o banco de dados.
- Repository (repository) Interfaces que fazem a comunicação com o banco de dados (via JPA/Hibernate).
- Service (service) Contém a lógica de negócio.
- impl: Implementações concretas dos serviços.
- Interfaces: Definem os contratos de cada serviço.
- Mapping (mapping) Faz a conversão entre Entity ↔ DTO.
- Exception (exception) Define exceções personalizadas e tratadores globais de erros.
- Resources (resources)
- templates (jte): Páginas da aplicação.
- static: Arquivos estáticos (CSS, imagens, ícones).
- application.yaml: Configurações do sistema.
- init.sql: Script de inicialização do banco.
- Testes (src/test) Contém os testes unitários e de integração.
