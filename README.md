# 🏥 Sistema Clínica - Trabalho WEB

Este projeto é um sistema web para gerenciamento de uma clínica médica, desenvolvido como parte de um trabalho da disciplina de Desenvolvimento Web.
O sistema permite o cadastro de pacientes e profissionais da saúde, além de agendamento e visualização de consultas.

---

## 🔧 Tecnologias Utilizadas

- Java 17/21
- Java 21 (upgrade realizado)
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

O projeto inclui um `Dockerfile` e um `docker-compose.yml` para facilitar a execução em outros computadores.

### 1. Construindo a imagem

```bash
# a partir da raiz do projeto
docker build -t ifba-medical-clinic:latest .
```

- usar o nome da imagem com prefixo local, por exemplo: `localhost/ifba-medical-clinic:latest` ao buildar e rodar;
- ou rodar com `podman` explicitamente: `podman run --rm -p 8082:8082 -e SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb" localhost/ifba-medical-clinic:latest`.

Exemplo rápido para construir e rodar com Podman:

```bash
podman build -t localhost/ifba-medical-clinic:latest .
podman run --rm -p 8082:8082 \
  -e SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb" \
  localhost/ifba-medical-clinic:latest
```

Se o build falhar com erros como "short-name ... did not resolve to an alias", "manifest unknown" ou erros de DNS ao puxar imagens base (ex.: `registry-1.docker.io`), veja as opções abaixo.

Opções quando o build falhar

- Usar a imagem runtime já empacotada (mais simples, evita puxar a imagem Maven): criei um `Dockerfile.runtime` que usa o JAR em `target/`:

```bash
# construir (garanta que o JAR exista: mvn -DskipTests clean package)
podman build -f Dockerfile.runtime -t localhost/ifba-medical-clinic:latest .
podman run --rm -p 8082:8082 \
  -e SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb" \
  localhost/ifba-medical-clinic:latest
```

- Se o Podman reclamar de nomes curtos, prefira usar prefixo `localhost/` ou `docker.io/` ao taggear a imagem (ex.: `localhost/ifba-medical-clinic:latest`).

- Se ocorrer `manifest unknown` para uma imagem base, a tag que você tentou pode não existir no registro. Use uma imagem base oficial conhecida (ex.: `openjdk:21-jre`) ou ajuste o `Dockerfile` para uma tag disponível.

```bash
# teste DNS/HTTP ao Docker Hub
curl -v https://registry-1.docker.io/v2/ || true
ping -c 3 registry-1.docker.io || true
```

- Como alternativa rápida (sem contêiner), rode o JAR localmente:

```bash
# a partir da raiz do projeto (assumindo que target/clinic-*.jar existe)
export SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb"
java -jar target/clinic-0.0.1-SNAPSHOT.jar
```

Configurar resolução de nomes curtos no Podman (opcional)

- Para permitir que Podman aceite short-names (por exemplo `maven:...`), adicione `docker.io` em `unqualified-search-registries` em `/etc/containers/registries.conf`:

```ini
unqualified-search-registries = ["docker.io"]
```

Edite com privilégios de root e reinicie o serviço do Podman se necessário. Se você não puder modificar o sistema, use sempre o prefixo `localhost/` ou `docker.io/` nas tags.

### 2. Executando com docker 

```bash
docker run --rm -p 8082:8082 \
  -e SPRING_DATASOURCE_URL="jdbc:h2:mem:testdb" \
  ifba-medical-clinic:latest
```

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
