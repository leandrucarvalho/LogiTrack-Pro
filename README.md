# LogiTrack Pro

MVP do desafio técnico LogiTrack Pro. Aplicação web em Spring Boot com módulo de manutenções (CRUD) e dashboard de indicadores calculados por SQL.

## URL do projeto
https://logitrack-pro.onrender.com/

## Stack
- Java 17
- Spring Boot (Web, Data JPA, Validation)
- Vue 3 (CDN)
- Spring Security (login com usuários persistidos)
- PostgreSQL
- Maven Wrapper

## Requisitos
- Java 17+
- PostgreSQL 14+ (ou equivalente)

## Como rodar localmente
1. Criar o banco e usuário (exemplo):
   - `createdb logitrack`
2. Executar o script SQL de carga inicial no banco.
3. Ajustar as credenciais no arquivo `src/main/resources/application.properties`.
4. Subir a aplicação:
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
Na primeira execução, um usuário administrador é criado automaticamente:
- Email: `admin@logitrack.local`
- Senha: `admin123`

## Decisões técnicas
- **Módulo escolhido**: Manutenções (CRUD) por atender bem o escopo e permitir as métricas do dashboard.
- **SQL nativo**: As 5 métricas do dashboard são calculadas via consultas SQL nativas conforme o requisito.
- **Separação por camadas**: Controller, Service, Repository, Model e DTO para manter responsabilidades claras.
- **Vue 3**: Interface SPA simples, consumindo APIs REST do backend.
- Spring Security: Autenticação via formulário e usuários persistidos no banco.

## Arquitetura (alto nível)
- `controller`: Endpoints web (dashboard e manutenções).
- `service`: Regras de negócio e orquestração das consultas.
- `repository`: Acesso a dados e consultas SQL nativas.
- `model`: Entidades JPA e enums.
- `dto`: Formulários e validação.

## Observações
- As tabelas do banco seguem o script fornecido, com mais 3 tabelas novas (veículos, viagens, manutenções, usuários, roles e usuario_roles).
- O dashboard usa a data atual do banco para filtrar manutenções futuras e o custo do mês corrente.
