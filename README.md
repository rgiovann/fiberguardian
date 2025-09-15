------------------------------------------------------------------------

# FiberGuardian – Backend

##  Visão Geral

O **FiberGuardian** é um sistema backend desenvolvido em **Spring Boot**
para gestão de fornecedores, produtos, notas fiscais e laudos
laboratoriais, com foco em segurança, rastreabilidade e integração com
frontend MPA.  
O projeto aplica **boas práticas de arquitetura (SOLID, DRY, KISS)** e
segue recomendações do **OWASP**, garantindo autenticação, proteção
contra CSRF, gerenciamento de sessão e auditoria.

------------------------------------------------------------------------

##  Arquitetura

- **Spring Boot** como framework principal.

- **Spring Security** com autenticação baseada em sessão (`JSESSIONID`),
  cookies HttpOnly e proteção CSRF.

- **JPA/Hibernate** para persistência de dados.

- **DTOs + Assemblers/Disassemblers** para separar entidades de modelos
  de entrada/saída.

- **AWS S3 / Local Storage** para armazenamento de arquivos (PDFs de
  notas fiscais e relatórios). S3 ainda não testado

------------------------------------------------------------------------

##  Segurança

- **Login/Logout seguro** via `AuthController`, com sessões gerenciadas
  e `HttpOnly cookies`.

- **Proteção CSRF** com cookie `XSRF-TOKEN` e cabeçalho `X-XSRF-TOKEN`.

- **Controle de acesso por roles**: `ADMIN`, `USUARIO`, `LABORATORIO`,
  `ENG_LAB`.

- **SameSite Cookies** configurados para reduzir riscos de CSRF.

- **Auditoria de operações** via `AuditorAwareImpl`, registrando o
  usuário autenticado em alterações.

------------------------------------------------------------------------

##  Principais Módulos

- **Controllers**

  - `AuthController` → Login/Logout.

  - `CsrfController` → Geração de token CSRF e validação de sessão.

  - `FornecedorController` → CRUD de fornecedores.

  - `ProdutoController` → CRUD de produtos.

  - `NotaFiscalController` → Cadastro de notas fiscais com upload de
    PDF.

  - `LaboratorioController` → Cadastro de laudos, geração de relatórios
    em PDF.

- **Assemblers/Disassemblers**
  Convertem objetos de entrada (`Input`) para entidades e entidades para
  DTOs.
  → Ex: `FornecedorInputDisassembler`, `FornecedorDtoAssembler`.

- **Configurações**

  - `SecurityConfig` → Regras de autenticação/autorização, CSRF, CORS.

  - `ModelMapperConfig` → Conversões complexas entre entidades e DTOs.

  - `StorageConfig` / `StorageProperties` → Configuração de
    armazenamento local ou AWS S3.

  - `JpaConfig` → Habilita auditoria.

------------------------------------------------------------------------

##  Endpoints Principais

- `POST /api/fg-login` → Autenticação (gera sessão + CSRF token).

- `POST /api/fg-logout` → Logout (invalida sessão).

- `GET /api/csrf-token` → Recupera token CSRF.

- `GET /api/fornecedores` → Listagem paginada de fornecedores.

- `POST /api/notas-fiscais` → Cadastro de nota fiscal com itens e PDF.

- `GET /api/laboratorios/paged` → Consultar laudos filtrados.

- `POST /api/laboratorios/pdf/{id}` → Gera relatório em PDF.

------------------------------------------------------------------------

##  Como Executar

1.  **Pré-requisitos**

    - Java 21+

    - Maven

    - Banco de dados (MariaDB/MySQL configurado no
      `application.properties`)

    - O banco de dados é gerenciado pelo Flyway e já contém dados de
      teste.

2.  **Clonar repositório**

    ``` bash
    git clone https://github.com/seu-repo/fiberguardian.git
    cd fiberguardian/backend
    ```

3.  **Build e execução**

    ``` bash
    mvn spring-boot:run
    ```

4.  **Acessar**

    - API root: `https://localhost:8443/api`

------------------------------------------------------------------------

##  Testes

- Teste endpoints com `curl`, `Postman`, existem scripts prontos em bash
  em `\backend\src\test\scripts_bash`

- Lembre-se: requisições `POST`, `PUT` e `DELETE` exigem **CSRF
  Token** + **JSESSIONID**.

------------------------------------------------------------------------

# FiberGuardian – Frontend

Sistema frontend usando arquitetura MPA com JavaScript Vanilla modular e
integração segura com Spring Boot.

## Arquitetura

- **JavaScript Modular**: Padrão IIFE com namespace
  `window.FiberGuardian`
- **MPA**: Páginas HTML estáticas servidas individualmente
- **Bootstrap**: Interface responsiva com componentes reutilizáveis
- **Fetch API**: Comunicação assíncrona com backend

## Segurança

- **CSRF**: Tokens automáticos via `/api/csrf-token` e cookies
  `XSRF-TOKEN`
- **Sessão**: Cookies HttpOnly com `credentials: 'include'`
- **Logout Seguro**: Endpoint `/api/fg-logout` com confirmação

##  Estrutura

    frontend/
    ├── assets/
    │   ├── js/........          # Módulos javascript
    │   ├── css/                 # Estilos customizados
    │   └── img/logo-fiberguardian.png
    ├── *.html                   # Páginas estáticas

## Principais Funcionalidades

- Autenticação com sessão persistente com gestão de tokens CSRF
- Interface responsiva e acessível com sistema modal unificado
- Tratamento robusto de erros
- Sistema de confirmação para ações críticas
- Download seguro de arquivos
- Dropdowns dinâmicos
- Navegação fluida entre seções

## Tecnologias

- **JavaScript Vanilla** (ES6+)
- **Bootstrap 5** (assumido pela estrutura modal)
- **Fetch API** nativo
- **Spring Boot** (backend integration)

## Rodando Frontend e Backend com Caddy e http-server

Este projeto utiliza Caddy como **reverse proxy unificado** para expor o
backend Spring Boot e o frontend estático via HTTPS.

### Justificativa do uso

- **Caddy** gerencia o tráfego HTTPS, roteando `/api/*` para o backend e
  `/*` para o frontend, simplificando a configuração local e garantindo
  HTTPS para ambos.
- **http-server** é usado para servir os arquivos estáticos do frontend
  com suporte a SSL, permitindo testes em HTTPS sem precisar configurar
  um servidor web complexo.
- O `.bat` combina ambos: inicia o `http-server` com SSL em background
  e, em seguida, inicia o Caddy, garantindo que o proxy funcione
  corretamente.

### Pré-requisitos de certificados

- **Frontend:** É necessário criar chaves autoassinadas `cert.pem` e
  `key.pem` para que o `http-server` sirva os arquivos estáticos via
  HTTPS.
- **Backend:** O Spring Boot deve ter o arquivo `.p12` correspondente
  para habilitar HTTPS no backend (porta 8443).

> ⚠️ Observação: Esses certificados são apenas para desenvolvimento
> local. Em produção, utilize certificados válidos.

### Como usar

1.  Certifique-se de ter o `http-server` instalado globalmente (via
    `npm install -g http-server`).
2.  Faça o download do caddy em `https://caddyserver.com/download`
3.  Ajuste os caminhos de `BASE_DIR`, `CERT_DIR` e `WEBAPP_DIR` conforme
    sua estrutura local.
4.  Chaves autoassinadas e criadas em `/frontend/cert` arquivo .p12 no
    diretório resource do spring
5.  Execute o arquivo `.bat`: \`\`\`bat start_run_project.bat

## Licença

Projeto de final de curso programa Entra21/Blusoft **. **\*
