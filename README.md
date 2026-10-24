# EcoGestor — Indicadores Ambientais para Empresas

Sistema para empresas controlarem seus indicadores ambientais, acompanhando o consumo de água e energia elétrica e a geração e reciclagem de resíduos.

Projeto Final da disciplina de Backend (Engenharia de Software, SATC). **Etapa 1: arquitetura do backend.**

## Índice

- [Integrantes](#integrantes)
- [O problema](#o-problema)
- [Tecnologias](#tecnologias)
- [Arquitetura](#arquitetura)
- [Entidades](#entidades)
- [Rotas](#rotas)
- [Como executar](#como-executar)
- [Situação do projeto](#situação-do-projeto)
- [Documentação](#documentação)

## Integrantes

| Nome | GitHub |
|---|---|
| Mirela | [@miniki7](https://github.com/miniki7) |
| Paola | [@PaolaBrina](https://github.com/PaolaBrina) |
| Alexsander | [@alexsanderantuneabarbosa](https://github.com/alexsanderantuneabarbosa) |

## O problema

**Tema sorteado:** Sustentabilidade e Meio Ambiente.

Muitas empresas controlam o consumo de água, energia elétrica e a geração de resíduos por meio de planilhas, contas e registros manuais. Como essas informações ficam espalhadas, torna-se difícil acompanhar os indicadores ambientais, identificar desperdícios e verificar se as metas de sustentabilidade estão sendo alcançadas.

O EcoGestor centraliza essas informações em uma API, permitindo cadastrar usuários, registrar seus indicadores ambientais mensalmente e definir metas de sustentabilidade. Com esses dados organizados, a empresa poderá acompanhar seu desempenho ambiental e identificar oportunidades de melhoria.

**Quem usa o sistema:**

| Quem | O que faz no sistema |
|---|---|
| Gestor ambiental | Registra os indicadores ambientais e acompanha os resultados |
| Responsável pela sustentabilidade | Define metas ambientais e verifica o cumprimento das regras |
| Administrador da empresa | Mantém os dados cadastrais dos usuários |

**Regras de negócio.** No mínimo 3, criadas pelo grupo a partir do problema: o que o sistema precisa garantir ou impedir. Precisam atender à necessidade do tema e estar implementadas no código. O que só confere formato (campo obrigatório, número positivo) é validação do DTO, não regra de negócio.

| # | Regra | Onde está no código | Rota que usa |
|---|---|---|---|
| R1 | Um usuário não pode ter mais de um indicador ambiental para o mesmo mês de referência | `IndicadorAmbientalService` | `POST /indicadores-ambientais` |
| R2 | A quantidade de resíduos reciclados não pode ser maior que a de resíduos gerados no mesmo indicador | `IndicadorAmbientalService` | `POST /indicadores-ambientais` |
| R3 | Um usuário não pode cadastrar duas metas para o mesmo indicador e o mesmo período de vigência | `MetaAmbientalService` | `POST /metas-ambientais` |

**Fora do escopo:** integração com sensores, leitura automática de contas, frontend completo e geração avançada de relatórios ambientais.

## Tecnologias

| O quê | Qual |
|---|---|
| Linguagem | Java 25 |
| Framework | Spring Boot 3.5 (Web, Validation, Data JPA) |
| Banco de dados | PostgreSQL 16, em Docker |
| Migrations | Flyway |
| Documentação da API | springdoc-openapi (Swagger UI) |

## Arquitetura

A API é organizada em camadas. Uma requisição entra pelo Controller, passa pelo Service, que aplica as regras de negócio, e chega ao banco pelo Repository; a resposta faz o caminho de volta. O Mapper converte DTO em entidade e entidade em DTO.

**Mapa da estrutura.** Uma linha por entidade, todas as do modelo. Em cada célula, a classe e a situação: **pronta**, **implementada** (já tem lógica), **em parte** (só uma parte das rotas) ou **montada** (criada, ligada às outras, ainda sem lógica).

| Entidade | Migration | Model | Repository | DTOs | Mapper | Service | Controller |
|---|---|---|---|---|---|---|---|
| Usuario | `V1__criar_usuarios.sql`, pronta | `Usuario`, pronta | `UsuarioRepository`, pronto | `UsuarioRequestDTO`, `UsuarioResponseDTO`, prontos | `UsuarioMapper`, implementado | `UsuarioService`, implementado | `UsuarioController`, implementado |
| IndicadorAmbiental | `V2__criar_indicadores_ambientais.sql`, pronta | `IndicadorAmbiental`, pronta | `IndicadorAmbientalRepository`, pronto | `IndicadorAmbientalRequestDTO`, `IndicadorAmbientalResponseDTO`, prontos | `IndicadorAmbientalMapper`, implementado | `IndicadorAmbientalService`, implementado (R1 e R2) | `IndicadorAmbientalController`, implementado |
| MetaAmbiental | `V3__criar_metas_ambientais.sql`, pronta | `MetaAmbiental`, pronta | `MetaAmbientalRepository`, pronto | `MetaAmbientalRequestDTO`, `MetaAmbientalResponseDTO`, prontos | `MetaAmbientalMapper`, implementado | `MetaAmbientalService`, implementado (R3) | `MetaAmbientalController`, implementado |

## Entidades

| Entidade | O que representa | Como se relaciona |
|---|---|---|
| Usuario | Um usuário (gestor, responsável ou administrador) cadastrado no EcoGestor | Possui vários indicadores ambientais e metas |
| IndicadorAmbiental | Os indicadores ambientais de um usuário em determinado mês (água, energia e resíduos) | Pertence a um usuário; pode ter várias metas |
| MetaAmbiental | Uma meta de sustentabilidade definida para um indicador e um período | Pertence a um usuário e a um indicador ambiental |

Atributos, tipos e o diagrama das tabelas estão em [docs/modelo-de-dominio.md](docs/modelo-de-dominio.md).

## Rotas

### As que já funcionam

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/usuarios` | Cadastra um usuário | Nenhuma | `201` | `400` |
| `GET` | `/usuarios` | Lista os usuários | Nenhuma | `200` | |
| `GET` | `/usuarios/{id}` | Busca um usuário pelo ID | Nenhuma | `200` | `404` |
| `POST` | `/indicadores-ambientais` | Cadastra o registro mensal de indicadores | R1 e R2 | `201` | `400`, `404` |
| `GET` | `/indicadores-ambientais` | Lista os indicadores ambientais | Nenhuma | `200` | |
| `GET` | `/indicadores-ambientais/{id}` | Busca um indicador pelo ID | Nenhuma | `200` | `404` |
| `POST` | `/metas-ambientais` | Cadastra uma meta ambiental | R3 | `201` | `400`, `404` |
| `GET` | `/metas-ambientais` | Lista as metas ambientais | Nenhuma | `200` | |
| `GET` | `/metas-ambientais/{id}` | Busca uma meta pelo ID | Nenhuma | `200` | `404` |

Exemplo de requisição:

```http
POST /indicadores-ambientais
Content-Type: application/json

{
  "idUsuario": 1,
  "mesReferencia": "2026-09-01",
  "consumoAgua": 420.5,
  "consumoEnergia": 1850.0,
  "residuosGerados": 250.0,
  "residuosReciclados": 180.0,
  "observacao": "Mês com produção normal"
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "idUsuario": 1,
  "mesReferencia": "2026-09-01",
  "consumoAgua": 420.5,
  "consumoEnergia": 1850.0,
  "residuosGerados": 250.0,
  "residuosReciclados": 180.0,
  "observacao": "Mês com produção normal"
}
```

### Erros

Todo erro sai no mesmo formato. Este é o `400` da regra R2 violada (`residuosGerados` 100 e `residuosReciclados` 150):

```json
{
  "status": 400,
  "erro": "Bad Request",
  "mensagem": "R2: a quantidade de resíduos reciclados não pode superar a quantidade de resíduos gerados.",
  "caminho": "/indicadores-ambientais",
  "timestamp": "2026-10-23T19:30:00",
  "campos": []
}
```

Um campo inválido também devolve `400`, com a lista `campos` dizendo qual campo corrigir. Um id que não existe devolve `404`.

### As planejadas

Estão no contrato, mas ainda não respondem.

| Recurso | O que vai fazer |
|---|---|
| Nenhuma | Todas as rotas planejadas já estão implementadas |

O contrato completo, com todas as rotas e os DTOs, está em [docs/contrato-da-api.md](docs/contrato-da-api.md).

## Como executar

Pré-requisitos: JDK 25, Docker Desktop aberto e Git.

```bash
git clone https://github.com/Miniki7/EcoGestor.git
cd EcoGestor

docker compose up -d          # sobe o PostgreSQL na porta 5437
./mvnw spring-boot:run        # Windows: .\mvnw.cmd spring-boot:run
```

Com a API no ar:

| Endereço | O que é |
|---|---|
| <http://localhost:8080/swagger-ui.html> | Swagger UI: as rotas, com **Try it out** para enviar requisições |
| <http://localhost:8080/v3/api-docs> | O contrato OpenAPI em JSON |

Para testar as rotas, use o Swagger UI ou o **Postman**. No Postman, importe todas as rotas de uma vez: **Import** → **Link** → `http://localhost:8080/v3/api-docs`.

Para parar: `Ctrl+C` na API e `docker compose stop` no banco. Para apagar o banco e rodar as migrations do zero: `docker compose down -v` e suba de novo.

## Situação do projeto

| Parte | Situação |
|---|---|
| Desenho | Completo: problema e regras neste README, modelo e contrato em docs/ |
| Estrutura | As 3 entidades têm migration, entidade, Repository, DTOs, Mapper, Service e Controller |
| API e regras | As rotas de usuários, indicadores ambientais e metas ambientais funcionam; R1, R2 e R3 estão implementadas |

**Ainda não funciona:** nada pendente nesta etapa.

## Documentação

| Documento | O que tem |
|---|---|
| [Modelo de domínio](docs/modelo-de-dominio.md) | Entidades, relacionamentos, diagrama das tabelas, migrations |
| [Contrato da API](docs/contrato-da-api.md) | Todas as rotas, os DTOs, exemplos de JSON e erros |
| [Autoavaliação](docs/autoavaliacao.md) | Os critérios da revisão do código, marcados pelo grupo |