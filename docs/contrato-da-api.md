# Contrato da API

## Convenções

| Item | Valor |
|---|---|
| Endereço base | `http://localhost:8080` |
| Formato | JSON, na requisição e na resposta |
| Datas | `yyyy-MM-dd` |
| Erros | Sempre no formato `ErroDTO` (ver [Erros](#erros)) |
| Documentação viva | `http://localhost:8080/swagger-ui.html` |

## Rotas

Coluna **Funciona**: `sim` para as rotas que já respondem nesta etapa: as da API simples e as que as regras de negócio usam.

### Usuários

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/usuarios` | Lista os usuários | Nenhuma | lista de `UsuarioResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/usuarios/{id}` | Busca um usuário | Nenhuma | `UsuarioResponseDTO` | `200` | `404` | sim |
| `POST` | `/usuarios` | Cadastra um usuário | `UsuarioRequestDTO` | `UsuarioResponseDTO` | `201` | `400` | sim |

### Indicadores ambientais

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/indicadores-ambientais` | Lista os indicadores | Nenhuma | lista de `IndicadorAmbientalResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/indicadores-ambientais/{id}` | Busca um indicador | Nenhuma | `IndicadorAmbientalResponseDTO` | `200` | `404` | sim |
| `POST` | `/indicadores-ambientais` | Cadastra o registro mensal de indicadores | `IndicadorAmbientalRequestDTO` | `IndicadorAmbientalResponseDTO` | `201` | `400`, `404` | sim |

### Metas ambientais

| Verbo | Caminho | O que faz | Entrada | Saída | Sucesso | Erros | Funciona |
|---|---|---|---|---|---|---|---|
| `GET` | `/metas-ambientais` | Lista as metas | Nenhuma | lista de `MetaAmbientalResponseDTO` | `200` | Nenhum | sim |
| `GET` | `/metas-ambientais/{id}` | Busca uma meta | Nenhuma | `MetaAmbientalResponseDTO` | `200` | `404` | sim |
| `POST` | `/metas-ambientais` | Cadastra uma meta ambiental | `MetaAmbientalRequestDTO` | `MetaAmbientalResponseDTO` | `201` | `400`, `404` | sim |

### Rotas que não são CRUD

As que nascem das regras de negócio do README. Estas precisam funcionar nesta etapa.

| Verbo | Caminho | O que faz | Regra | Sucesso | Erros |
|---|---|---|---|---|---|
| `POST` | `/indicadores-ambientais` | Registra os indicadores do mês, recusando mês repetido e reciclados acima de gerados | R1 e R2 | `201` | `400`, `404` |
| `POST` | `/metas-ambientais` | Cadastra a meta, recusando duplicidade de indicador e período | R3 | `201` | `400`, `404` |

## DTOs

O que entra não é igual ao que sai: o cliente não envia `id` nem campos que a API decide.

| DTO | Direção | Campos | Validações |
|---|---|---|---|
| `UsuarioRequestDTO` | entrada | `nome`, `email`, `senha`, `tipoUsuario` | todos obrigatórios (`@NotBlank`); `email` com `@Email` |
| `UsuarioResponseDTO` | saída | `id`, `nome`, `email`, `tipoUsuario`, `dataCadastro` | Nenhuma (a senha nunca é devolvida) |
| `IndicadorAmbientalRequestDTO` | entrada | `idUsuario`, `mesReferencia`, `consumoAgua`, `consumoEnergia`, `residuosGerados`, `residuosReciclados`, `observacao` | todos obrigatórios (`@NotNull`), exceto `observacao`; valores numéricos `@PositiveOrZero` |
| `IndicadorAmbientalResponseDTO` | saída | `id`, `idUsuario`, `mesReferencia`, `consumoAgua`, `consumoEnergia`, `residuosGerados`, `residuosReciclados`, `observacao` | Nenhuma |
| `MetaAmbientalRequestDTO` | entrada | `idUsuario`, `idIndicador`, `descricao`, `valorMeta`, `dataInicio`, `dataFim` | todos obrigatórios (`@NotNull`/`@NotBlank`); `valorMeta` `@PositiveOrZero` |
| `MetaAmbientalResponseDTO` | saída | `id`, `idUsuario`, `idIndicador`, `descricao`, `valorMeta`, `dataInicio`, `dataFim`, `status` | Nenhuma |

## Exemplos

Um par requisição/resposta por entidade.

### `POST /usuarios`

Requisição:

```json
{
  "nome": "Maria Souza",
  "email": "maria@email.com",
  "senha": "123456",
  "tipoUsuario": "pessoa física"
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "nome": "Maria Souza",
  "email": "maria@email.com",
  "tipoUsuario": "pessoa física",
  "dataCadastro": "2026-10-23T19:30:00"
}
```

### `POST /indicadores-ambientais`

Requisição:

```json
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

### `POST /metas-ambientais`

Requisição:

```json
{
  "idUsuario": 1,
  "idIndicador": 1,
  "descricao": "Reduzir o consumo de água",
  "valorMeta": 400.0,
  "dataInicio": "2026-10-01",
  "dataFim": "2026-12-31"
}
```

Resposta, `201 Created`:

```json
{
  "id": 1,
  "idUsuario": 1,
  "idIndicador": 1,
  "descricao": "Reduzir o consumo de água",
  "valorMeta": 400.0,
  "dataInicio": "2026-10-01",
  "dataFim": "2026-12-31",
  "status": "pendente"
}
```

## Erros

Todo erro sai no mesmo formato. Este é o `404` de `GET /usuarios/999`:

```json
{
  "status": 404,
  "erro": "Not Found",
  "mensagem": "Usuário não encontrado: 999",
  "caminho": "/usuarios/999",
  "timestamp": "2026-10-23T19:30:00",
  "campos": []
}
```

No `400` de validação, a lista `campos` traz um item por campo inválido: `{"campo": "email", "mensagem": "E-mail é obrigatório"}`.

| Status | Quando acontece neste projeto |
|---|---|
| `400` | JSON ilegível ou campo inválido no DTO de entrada |
| `404` | Id que não existe |
| `400` | Regra de negócio violada: a `mensagem` diz qual regra barrou o pedido |