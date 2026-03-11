# LogiTrack Pro

MVP do desafio tecnico LogiTrack Pro. Aplicacao web em Spring Boot com modulo de manutencoes (CRUD) e dashboard de indicadores calculados por SQL.

## Stack
- Java 17
- Spring Boot (Web, Data JPA, Validation, Thymeleaf)
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
   - `http://localhost:8080/dashboard`
   - `http://localhost:8080/maintenances`

## Decisoes tecnicas
- **Modulo escolhido**: Manutencoes (CRUD) por atender bem o escopo e permitir as metricas do dashboard.
- **SQL nativo**: As 5 metricas do dashboard sao calculadas via consultas SQL nativas conforme o requisito.
- **Separacao por camadas**: Controller, Service, Repository, Model e DTO para manter responsabilidades claras.
- **Thymeleaf**: Interface simples e funcional sem necessidade de SPA para o MVP.

## Arquitetura (alto nivel)
- `controller`: Endpoints web (dashboard e manutencoes).
- `service`: Regras de negocio e orquestracao das consultas.
- `repository`: Acesso a dados e consultas SQL nativas.
- `model`: Entidades JPA e enums.
- `dto`: Formularios e validacao.

## Observacoes
- As tabelas do banco seguem o script fornecido (veiculos, viagens, manutencoes).
- O dashboard usa a data atual do banco para filtrar manutencoes futuras e o custo do mes corrente.
