<h1 align="center">Event Service</h1>

----
<p align="center">
<a href="https://github.com/FellipeToledo/Urban-Mobility-Management-System">
  <em>Serviço responsável pelo cadastro, gerenciamento e rastreamento de eventos, tanto programados (como interdições planejadas) quanto não programados (como acidentes), garantindo que as informações sobre esses eventos sejam processadas e disponibilizadas em tempo real.
</p>

<p align="center">
  <a href="#exemplos">Exemplos</a> •
  <a href="#issues">Issues</a> •
</p>

## Funcionalidades

* criação de um novo evento.
* Recuperação de todos os eventos cadastrados.
* Recuperação dos detalhes de um evento específico com base no ID fornecido.
* Atualização de um evento específico.
* Exclusão de um evento existente.
* Filtro dos eventos com base nos parâmetros fornecidos.

## Exemplo: CRUD de Eventos (com JPA e Spring Data JPA)
### - Criar Evento Programado (POST)

- Endpoint: /api/v1/event/scheduled
- Método: POST
- Descrição: Permite a criação de um novo evento programado. O evento é cadastrado no banco de dados com informações como descrição, ID da regulamentação, data da regulamentação, data da interdição, bairro, criticidade, status e uma lista de vias bloqueadas com detalhamento do trecho interditado.
- Corpo da requisição:
```json
{
  "description": "IÇAMENTO DE EQUIPAMENTO",
  "regulationId": "TR/SUBTT/CRV Nº 9.757",
  "regulationDate": "2024-10-19",
  "roadblockDate": "2022-10-31T09:30:00Z",
  "neighborhood": "Centro",
  "severity": "Medium",
  "status": "Open",
  "roadblocks": [
    {
      "road": "Rua Carlos Sampaio",
      "startRoad": "Praça da Cruz Vermelha",
      "endRoad": "Rua Washington Luís",
      "startDateTime": "2022-10-19T22:00:00Z",
      "endDateTime": "2022-10-20T05:00:00Z"
    }
  ]
}
```
> Resposta: Retorna o evento criado com seu ID gerado, assim como a data e hora da criação.<br/>
> Código de Resposta: 201 Created

### - Criar Evento Não Programado (POST)

- Endpoint: /api/v1/event/unscheduled
- Método: POST
- Descrição: Permite a criação de um novo evento não programado. O evento é cadastrado no banco de dados com informações como categoria, descrição, criticidade, status e uma lista de vias bloqueadas com detalhamento do trecho interditado.
- Corpo da requisição:
```json
{
  "category": "Acidente",
  "description": "Acidente envolvendo um Ônibus e um veículo de passeio",
  "severity": "High",
  "status": "In Progress",
  "roadblocks": [
    {
      "road": "Avenida Presidente Vargas",
      "startRoad": "Avenida Passos",
      "endRoad": "Avenida Rio Branco",
      "startDateTime": "2024-10-21T22:00:00Z",
      "endDateTime": "2024-10-21T23:30:00Z"
    }
  ]
}
```
> Resposta: Retorna o evento criado com seu ID gerado, assim como a data e hora da criação.<br/>
> Código de Resposta: 201 Created

### - Buscar Todos os Eventos (GET)

- Endpoint: /api/v1/all
- Método: GET
- Descrição: Recupera todos os eventos cadastrados no sistema. Os eventos são retornados em formato de lista, com informações completas.

> Resposta: Retorna uma lista de todos os eventos cadastrados.
> Código de Resposta: 200 OK | 204 No Content<br/>
>

### - Buscar Evento por ID (GET)

- Endpoint: /api/v1/event/{id}
- Método: GET
- Descrição: Recupera os detalhes de um evento específico com base no ID fornecido.
- Parâmetro de URL: Id - ID do evento a ser buscado.

> Resposta: Retorna o evento com o ID passado como argumento.
> Código de Resposta: 200 OK | 404 Not Found>


### - Atualizar Evento Programado (PUT)

- Endpoint: /api/v1/event/scheduled/{id}
- Método: PUT
- Descrição: Atualiza um evento programado existente no sistema. É necessário fornecer o ID do evento na URL, e os dados no corpo da requisição serão usados para atualizar o evento.
- Parâmetro de URL: Id - ID do evento a ser atualizado.
- Corpo da requisição:

```json
{
  "description": "IÇAMENTO DE EQUIPAMENTO",
  "regulationId": "TR/SUBTT/CRV Nº 9.757",
  "regulationDate": "2024-10-19",
  "roadblockDate": "2022-10-31T09:30:00Z",
  "neighborhood": "Centro",
  "severity": "High", <-- Update
  "status": "Open",
  "roadblocks": [
    {
      "road": "Rua Carlos Sampaio",
      "startRoad": "Praça da Cruz Vermelha",
      "endRoad": "Rua Washington Luís",
      "startDateTime": "2022-10-19T22:00:00Z",
      "endDateTime": "2022-10-20T05:00:00Z"
    }
  ]
}
```
> Resposta: Retorna o evento atualizado.<br/>
> Código de Resposta:<br/>
> 200 OK - Se a requisição for bem sucedida <br/>
> 404 Not Found - Se o Evento não existir<br/>
> 400 Bad Request - Se o Id fornecido não corresponder a um Evento Programado

### - Atualizar Evento Não Programado (PUT)

- Endpoint: /api/v1/event/unscheduled/{id}
- Método: PUT
- Descrição: Atualiza um evento não programado existente no sistema. É necessário fornecer o ID do evento na URL, e os dados no corpo da requisição serão usados para atualizar o evento.
- Parâmetro de URL: Id - ID do evento a ser atualizado.
- Corpo da requisição:

```json
{
  "category": "Acidente",
  "description": "Acidente envolvendo um Ônibus e um Utilitário", <-- Update
  "severity": "High",
  "status": "Open", <-- Update
  "roadblocks": [
    {
      "road": "Avenida Presidente Vargas",
      "startRoad": "Avenida Passos",
      "endRoad": "Avenida Rio Branco",
      "startDateTime": "2024-10-21T22:00:00Z",
      "endDateTime": "2024-10-21T23:30:00Z"
    }
  ]
}
```
> Resposta: Retorna o evento atualizado.<br/>
> Código de Resposta:<br/> 
> 200 OK - Se a requisição for bem sucedida <br/>
> 404 Not Found - Se o Evento não existir<br/> 
> 400 Bad Request - Se o Id fornecido não corresponder a um Evento Não Programado


### - Excluir Evento (DELETE)

- Endpoint: /api/v1/event/{id}
- Método: DELETE
- Descrição: Exclui um evento existente no sistema com base no ID fornecido.
- Parâmetro de URL: Id - ID do evento a ser excluído.
  
> Código de Resposta:<br/> 
> 204 No Content - Se a requisição for bem sucedida<br/>
> 404 Not Found - Se o Evento não existir.


💡 Funcionalidades Extras (opcional para refinar o CRUD)
- Filtros de Eventos por Severidade, Status ou Localização
    - Endpoint: /api/v1/event?severity=High&status=Open
    - Método: GET
    - Descrição: Filtra os eventos com base em parâmetros como severidade, status ou localização. Facilita a consulta personalizada dos eventos que impactam a mobilidade urbana.

## 🛠 Tecnologias

- Tecnologias utilizadas no projeto:
  - Spring:
    - [JPA](https://github.com/spring-projects/spring-data-jpa) - Persistência
    - [Actuator](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-actuator) - Monitoramento e gestão
  - Banco de dados:
      - [H2](https://github.com/h2database/h2database) - Desenvolvimento / teste
      - [PostgreSQL](https://github.com/postgres/postgres) - Produção
  - Documentação da API:
      - [Swagger UI](https://github.com/swagger-api/swagger-ui)

## Issues

Quer Contribuir? Busque por [Primeira Issue][good-first-issue] label.

### 🐛 Bugs

Registre um problema devido a bugs, documentação ausente ou comportamento inesperado.

[**Veja Bugs**][bugs]

### 💡 Requisição de funcionalidade

Registre um issue para sugerir novos recursos. Vote nas solicitações de recursos adicionando
um 👍. Isso me ajuda a priorizar o que trabalhar.
[**Veja requisição de funcionalidade**][requests]

### Licença

[MIT](LICENSE)

<div align="center">

![screenshot](https://raw.githubusercontent.com/FellipeToledo/files/refs/heads/main/github-desktop.svg) [@FellipeToledo](https://github.com/FellipeToledo) &nbsp;&middot;&nbsp;   ![screenshot](https://raw.githubusercontent.com/FellipeToledo/files/refs/heads/main/linkedin-outlined.svg) [@FellipeToledo](https://www.linkedin.com/in/fellipetoledo/) &nbsp;&middot;&nbsp;

</div>

<!-- prettier-ignore-start -->

[build-badge]: https://img.shields.io/github/actions/workflow/status/testing-library/react-testing-library/validate.yml?branch=main&logo=github
[build]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/actions
[coverage-badge]: https://img.shields.io/codecov/c/github/testing-library/react-testing-library.svg?style=flat-square
[coverage]: https://app.codecov.io/github/FellipeToledo/Urban-Mobility-Management-System
[license-badge]: https://img.shields.io/npm/l/@testing-library/react.svg?style=flat-square
[license]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/blob/main/LICENSE
[prs-badge]: https://img.shields.io/badge/PRs-welcome-brightgreen.svg?style=flat-square
[prs]: http://makeapullrequest.com
[coc-badge]: https://img.shields.io/badge/code%20of-conduct-ff69b4.svg?style=flat-square
[coc]: https://github.com/testing-library/react-testing-library/blob/main/CODE_OF_CONDUCT.md
[github-watch-badge]: https://img.shields.io/github/watchers/testing-library/react-testing-library.svg?style=social
[github-watch]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/watchers
[github-star-badge]: https://img.shields.io/github/stars/testing-library/react-testing-library.svg?style=social
[github-star]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/stargazers
[emojis]: https://github.com/all-contributors/all-contributors#emoji-key
[all-contributors]: https://github.com/all-contributors/all-contributors
[all-contributors-badge]: https://img.shields.io/github/all-contributors/testing-library/react-testing-library?color=orange&style=flat-square
[bugs]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/issues?q=is%3Aissue+is%3Aopen+label%3Abug+sort%3Acreated-desc
[requests]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/issues?q=is%3Aissue+sort%3Areactions-%2B1-desc+label%3Aenhancement+is%3Aopen
[good-first-issue]: https://github.com/FellipeToledo/Urban-Mobility-Management-System/issues?utf8=✓&q=is%3Aissue+is%3Aopen+sort%3Areactions-%2B1-desc+label%3A"good+first+issue"+

<!-- prettier-ignore-end -->