# 🛰️ OrbitAlert — Java Advanced API

> **FIAP Global Solution 2026/1 · Turma 2TDS Fevereiro · Tema: Economia Espacial**

---

## 📋 Descrição

O **OrbitAlert** é uma plataforma de alertas precoces de desastres naturais que combina dados do satélite Sentinel-1 (ESA/Copernicus) com inteligência artificial generativa para antecipar eventos de deslizamento e enchente com até 48 horas de antecedência.

Esta API Java Spring Boot é o **hub central** da solução — gerencia o ciclo de vida dos alertas, persiste leituras IoT, aciona a Claude API para análise em linguagem natural e serve o aplicativo mobile.

---

## 👥 Equipe

| Nome | RM |
|---|---|
| Gabriel Sbrana Campos | RM 565849 |
| Moisés Waidemann | RM 563719 |
| Thiago Rodrigues da Mota | RM 563650 |
| Richard Freitas | RM 566127 |

---

## 🔗 Links

| Recurso | Link |
|---|---|
| 🚀 **Deploy ** | `https://java-advanced-2-7tix.onrender.com` |
| 📄 **Swagger / OpenAPI** | `https://java-advanced-2-7tix.onrender.com/swagger-ui/index.html` |
| 🎥 **Vídeo de Apresentação** | `https://www.youtube.com/watch?v=ach61l1-NzU` |
| 🎥 **Vídeo pitch** | ` https://youtu.be/96OZFLMUHDs` |
| 📦 **GitHub** | `https://github.com/GS-2TDSPF/JAVA-ADVANCED` |

Acesse o link do swagger para fazer teste na API
---

## 🏗️ Arquitetura

```
React Native (App Mobile)
        │ HTTPS + JWT
        ▼
Java Spring Boot (API Central) ←── ESP32 POST /leitura-iot/mqtt
        │
        ├── Oracle DB (oracle.fiap.com.br:1521:ORCL)
        └── Claude API (Análise IA por alerta)
```

### Stack

| Camada | Tecnologia |
|---|---|
| Backend | Java 21 + Spring Boot 4.0.6 |
| ORM | Spring Data JPA + Hibernate + Oracle Dialect |
| Banco de Dados | Oracle DB FIAP |
| Segurança | Spring Security + JWT (jjwt 0.12.6) |
| Documentação | SpringDoc OpenAPI 3 / Swagger |
| Cache | Spring Cache (Simple) |
| Mapeamento | MapStruct 1.6.3 |
| IoT | ESP32 → POST /leitura-iot/mqtt |

---

## 📦 Estrutura de Pacotes

```
br.com.fiap.orbitAlert
├── config/          → SwaggerConfig, CorsConfig, GlobalExceptionHandler, Exceptions
├── control/         → Controllers (11) + AutenticacaoController
├── dto/             → DTOs (11)
├── mapper/          → MapStruct Interfaces (11)
├── model/           → Entidades JPA (12) + BaseEntity
│   └── enums/       → StatusAlertaEnum, TipoPerfilEnum, TipoNotificacaoEnum
├── records/         → AlertaResumoRecord, LeituraIotRecord, MunicipioRiscoRecord, ErroResponseRecord
├── repository/      → JPA Repositories (11)
├── security/        → JWTUtil, JWTAuthFilter, SegurancaConfig, UsuarioConfig, AuthManager
└── service/         → CachingServices (10) + PaginacaoServices (10)
```

---

## 🗄️ Banco de Dados

Utiliza Oracle DB com tabelas criadas via DDL. O JPA apenas mapeia — **não recria o banco** (`ddl-auto=none`).

### Tabelas Mapeadas

```
TB_LOG_ERRO            TB_USUARIO
TB_MUNICIPIO           TB_USUARIO_MUNICIPIO
TB_ZONA_RISCO          TB_ESTACAO_IOT
TB_LEITURA_IOT         TB_TIPO_ALERTA
TB_ALERTA              TB_ANALISE_IA
TB_HISTORICO_ALERTA    TB_NOTIFICACAO
```

---

## 🔐 Autenticação — Passo a Passo

A API usa **Spring Security + JWT**. Siga esta ordem obrigatória:

### 1. Criar usuário (sem token)

```http
POST /usuario/novo
Content-Type: application/json

{
  "nmUsuario": "Gabriel Sbrana",
  "dsEmail": "gabriel@orbitalert.com.br",
  "dsSenhaHash": "minhasenha123",
  "tpPerfil": "ADMIN",
  "stAtivo": "S"
}
```

> A senha é enviada em texto plano — a API encoda com **BCrypt** automaticamente antes de salvar.

---

### 2. Autenticar e obter o token

```http
POST /autenticacao/login?email=gabriel@orbitalert.com.br&password=minhasenha123&duracao=60
```

**Resposta:**
```
eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJnYWJyaWVsQG9yYml0YWxlcnQuY29tLmJyIi...
```

> O token tem validade de **60 minutos** por padrão.

---

### 3. Usar o token em todas as requisições protegidas

```http
GET /alerta/todos
Authorization: Bearer eyJhbGciOiJIUzI1NiJ9...
```

No Swagger, clique em **Authorize** (cadeado) e cole:
```
Bearer eyJhbGciOiJIUzI1NiJ9...
```

---

## 🚀 Como Rodar Localmente

### Pré-requisitos

- Java 21+
- Maven 3.8+
- IntelliJ IDEA ou VS Code com extensão Java
- Acesso à rede FIAP ou VPN (`oracle.fiap.com.br:1521:ORCL`)

### Passos

**1. Clone o repositório**
```bash
git clone https://github.com/orbitalert-gs/orbitalert-java.git
cd orbitalert-java
```

**2. Configure suas credenciais no `application.properties`**
```properties
spring.datasource.username=SEU_RM
spring.datasource.password=SUA_SENHA_FIAP
```

**3. Execute os scripts SQL na ordem**
```
01_create_tables.sql
02_procedures_carga.sql
03_carga_dados.sql
```

**4. Rode a aplicação**
```bash
mvn spring-boot:run
```

Ou pelo IntelliJ: execute `ProjetoOrbitAlertApplication.java`

**5. Acesse o Swagger**
```
http://localhost:8080/swagger
```

**6. Siga o fluxo de autenticação descrito acima**

---

## 📡 Endpoints

| Recurso | Base URL | Endpoints extras |
|---|---|---|
| Autenticação | `/autenticacao` | `POST /login` |
| Usuários | `/usuario` | `/perfil`, `/municipio/{id}` |
| Municípios | `/municipio` | `/dashboard` (Record), `/com-alertas` |
| Vínculos | `/usuario-municipio` | `/usuario/{id}`, `/municipio/{id}` |
| Zonas de Risco | `/zona-risco` | — |
| Estações IoT | `/estacao-iot` | — |
| Leituras IoT | `/leitura-iot` | `POST /mqtt` (ESP32) |
| Tipos de Alerta | `/tipo-alerta` | — |
| Alertas | `/alerta` | `/resumo` (Record), `/status`, `/municipio/{id}`, `/criticos` |
| Análises IA | `/analise-ia` | — |
| Histórico Alertas | `/historico-alerta` | — |
| Notificações | `/notificacao` | — |

Padrão de cada recurso:

```
GET    /todos           → lista todos
GET    /paginar         → paginado (?page=0&size=5)
GET    /{id}            → por ID com HATEOAS
POST   /novo            → criar
PUT    /atualizar/{id}  → atualizar
DELETE /remover/{id}    → remover
```

### Endpoints públicos (sem token)

```
POST /usuario/novo
POST /autenticacao/login
GET  /swagger-ui/**
GET  /v3/**
GET  /swagger/**
```

---

## 🧪 Testando no Swagger

1. Acesse `http://localhost:8080/swagger`
2. Execute `POST /usuario/novo` para criar seu usuário
3. Execute `POST /autenticacao/login` com email e senha — copie o token retornado
4. Clique em **Authorize** (cadeado no topo) e cole: `Bearer <seu_token>`
5. Todos os endpoints estarão liberados

---

## ✅ Requisitos Técnicos Atendidos

- [x] API REST com verbos HTTP e status codes corretos (200, 201, 204, 400, 404, 422, 500)
- [x] HATEOAS nos endpoints `GET /{id}`
- [x] Entidades JPA com relacionamentos (`@OneToMany`, `@ManyToOne`, `@OneToOne`)
- [x] Herança — `BaseEntity` com `DT_CADASTRO` estendida por `Usuario`, `Municipio` e `ZonaRisco`
- [x] Chave composta — `UsuarioMunicipioPK` com `@EmbeddedId` em `UsuarioMunicipio`
- [x] Bean Validation nos DTOs (`@NotBlank`, `@Email`, `@Min`, `@Max`)
- [x] Java Records — `AlertaResumoRecord`, `LeituraIotRecord`, `MunicipioRiscoRecord`, `ErroResponseRecord`
- [x] MapStruct para conversão entre entidade e DTO
- [x] Paginação com `PageRequest`
- [x] Cache com `@Cacheable` e `@CacheEvict`
- [x] Tratamento global de exceções com `@RestControllerAdvice`
- [x] Spring Security + JWT — stateless, BCrypt, endpoints públicos configurados
- [x] Swagger com autenticação Bearer JWT
- [x] CORS configurado para app mobile

---

## 🔗 Repositórios do projeto

| Disciplina | Repositório | Status |
|---|---|---|
| Java Advanced | orbitalert-java ← você está aqui | ✅ |
| .NET | [orbitalert-dotnet](#) | 🔧 |
| Banco de Dados | [orbitalert-database](#) | ✅ |
| Mobile | [orbitalert-mobile](#) | 🔧 |
| IoT | [orbitalert-iot](#) | 🔧 |
| DevOps | [orbitalert-devops](#) | 🔧 |
| Compliance & QA | [orbitalert-qa](#) | ✅ |

---

## 📚 Disciplina

**Java Advanced** — FIAP 2026 · 1º Semestre · Turma 2TDS Fevereiro
Global Solution 2026/1 — Tema: **Economia Espacial** 
