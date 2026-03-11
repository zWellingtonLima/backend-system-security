# Serviços para construir

## Autenticação e Sessões
Login / Logout

- POST /auth/login
- POST /auth/logout

## Sessoes
- GET /sessoes
- GET /sessoes/:id
- POST /sessoes
- GET /sessoes/:id/encerrar

Por exemplo, quando recebe um post em /sessoes ele cria o hora_entrada e id_user
Pra encerrar, /sessoes/12/encerrar

## Users (segurancas)
- GET /users
- GET /users/:id
- POST /users

## Funcionarios
- GET /funcionarios
- GET /funcionarios/:id
- POST /funcionarios

## Visitantes
- GET /visitantes
- GET /visitantes/:id
- POST /visitantes

Por exemplo na hora da criacao
```json
{
  "nome_visitante": "Carlos Silva",
  "empresa": "Vodafone",
  "documento_identificacao": "CC123456",
  "setor_destino": "TI"
}
```

## Visitas
- GET /visitas
- GET /visitas/:id
- POST /visitas

O POST /visitas recebe
```json
{
  "tipo_visita": "SERVICO",
  "id_visitante": 3,
  "id_funcionario_responsavel": null,
  "observacoes": "Manutenção rede"
}
```

## Movimentacoes
- POST /movimentacoes

Aqui podem existir diferentes eventos.
### Quando um funcionario entra
```json
{
  "hora_entrada": "2026-03-09T08:00:00",
  "id_funcionario": 4
}
```

### Quando um visitante entra
```json
{
  "hora_entrada": "2026-03-09T10:00:00",
  "id_visita": 7
}
```

Quando alguem for sair
```json
{
  "hora_saida": "2026-03-09T12:00:00"
}
```

### Consultas nas movimentacoes
- GET /movimentacoes
- GET /movimentacoes/:id
- GET /movimentacoes/ativas -- Quem esta no predio
- GET /movimentacoes/hoje

## Salas
- GET /salas
- GET /salas/:id
- POST /salas

## Tipos de chave
- GET /tipo-chaves
- GET /tipo-chaves/:id
- POST /tipo-chaves

## Chaves
- GET /chaves
- GET /chaves/:id
- POST /chaves

### Alguns filtros pra implementar 
GET /chaves?status=DISPONIVEL
GET /chaves?sala=10

## Entrega Chave
- POST /entrega-chaves
Na hora da criacao registra a hora_entrega precisando enviar apenas o id_chave

### Devolver a chave
- PATCH /entregas-chaves/:id/devolver
E essa registra a hora_devolucao

### Uteis para consultas e Dashboard
- GET /entregas-chaves
- GET /entregas-chaves/:id
- GET /entregas-chaves/ativas

## Consumos
- GET /consumos
- GET /consumos/:id
- POST /consumos

### Filtros
- GET /consumos?tipo=Luz

## Rondas
- GET /rondas
- GET /rondas/:id
- POST /rondas

### Registrar uma Ronda
```json
{
  "hora_ronda": "2026-03-09T22:00:00",
  "ocorrencias": "Tudo normal",
  "id_user": 1
}
```
- A ronda pode ser registrada após ele já a ter feito, uma vez que as ocorrências só podem ser inseridas caso ele tenha visto algo.

## Criar endpoint para o dashboard
- Quem está no predio
  - GET /dashboard/pessoas-no-predio -- e implementamos o que é necessário para informar isso
- As chaves que foram emprestadas
  - GET /dashboard/chaves-emprestadas
- Visitas do dia
  - GET /dashboard/visitas-hoje

# Observações: 
- Nenhum desses endpoints, da forma como foi planejado aqui, possibilita que a pessoa altere alguma informação. Por exemplo, se o segurança
errar na hora de colocar um consumo ele não tem como alterar. Isso acontece em vários outros casos.

# Possíveis melhorias
Podemos adicionar a questão de CASCADE, UPDATE, RESTRICT, implementar índices nas tabelas que forem mais buscadas no sistema.