# LogiTrack Pro

MVP do desafio tecnico LogiTrack Pro. Aplicacao web em Spring Boot com modulo de manutencoes (CRUD) e dashboard de indicadores calculados por SQL.

## Stack
- Java 17
- Spring Boot (Web, Data JPA, Validation)
- Vue 3 (CDN)
- Spring Security (login com usuarios persistidos)
- PostgreSQL
- Maven Wrapper

## Requisitos
- Java 17+
- PostgreSQL 14+ (ou equivalente)

## Como rodar localmente
1. Criar o banco e usuario (exemplo):
   - `createdb logitrack`
2. Executar o script SQL de carga inicial no banco.
3. Ajustar as credenciais no arquivo `src/main/resources/application.properties`.
4. Subir a aplicacao:
   - `mvn spring-boot:run`
5. Acessar:
   - `http://localhost:8080/`

## Deploy no Render (resumo)
1. Crie um banco Postgres no Render.
2. Crie um Web Service a partir do seu repo (Render vai usar o `Dockerfile`).
3. Configure env vars no Render:
   - `SPRING_DATASOURCE_URL` (JDBC)
   - `SPRING_DATASOURCE_USERNAME`
   - `SPRING_DATASOURCE_PASSWORD`
   - `PORT` (Render injeta automaticamente; o app usa `server.port=${PORT:8080}`)

Exemplo de JDBC:
`jdbc:postgresql://<host>:<port>/<db>`

## Login
Na primeira execucao, um usuario administrador e criado automaticamente:
- Email: `admin@logitrack.local`
- Senha: `admin123`

## Decisoes tecnicas
- **Modulo escolhido**: Manutencoes (CRUD) por atender bem o escopo e permitir as metricas do dashboard.
- **SQL nativo**: As 5 metricas do dashboard sao calculadas via consultas SQL nativas conforme o requisito.
- **Separacao por camadas**: Controller, Service, Repository, Model e DTO para manter responsabilidades claras.
- **Vue 3**: Interface SPA simples, consumindo APIs REST do backend.
- **Spring Security**: Autenticacao via formulario e usuarios persistidos no banco.

## Arquitetura (alto nivel)
- `controller`: Endpoints web (dashboard e manutencoes).
- `service`: Regras de negocio e orquestracao das consultas.
- `repository`: Acesso a dados e consultas SQL nativas.
- `model`: Entidades JPA e enums.
- `dto`: Formularios e validacao.

## Observacoes
- As tabelas do banco seguem o script fornecido (veiculos, viagens, manutencoes).
- O dashboard usa a data atual do banco para filtrar manutencoes futuras e o custo do mes corrente.
