<h1 align="center">UMMS</h1>

<p align="center">
<a href="https://github.com/FellipeToledo/Urban-Mobility-Management-System">
  <img src="https://raw.githubusercontent.com/FellipeToledo/files/refs/heads/main/urban-mobility-management-system-high-resolution-logo-transparent%20(4).png" alt="umms-logo" width="750px" height="120px"/></a>
  <br>
  <em>O Urban Mobility Management System é uma plataforma que gerencia eventos de trânsito em tempo real
    <br> otimizando o tráfego e melhorando a mobilidade urbana.</em>
  <br>
</p>

<p align="center">
  <a href="#instalação">Instalação</a>  •
  <a href="#exemplos">Exemplos</a> •
  <a href="#issues">Issues</a> •
  <a href="#-bugs">🐛Bugs</a> •
  <a href="#-requisição-de-funcionalidade">Requisição de funcionalidade</a> •
  <a href="#licença">Licença</a>
</p>

<div align="center" >
  
<!-- prettier-ignore-start -->
[![Build Status][build-badge]][build]
[![Code Coverage][coverage-badge]][coverage]
[![MIT License][license-badge]][license]
[![PRs Welcome][prs-badge]][prs]
[![Code of Conduct][coc-badge]][coc]

[![Watch on GitHub][github-watch-badge]][github-watch]
[![Star on GitHub][github-star-badge]][github-star]
<!-- prettier-ignore-end -->
</div>

## Funcionalidades

* criação de um novo evento.
* Recuperação de todos os eventos cadastrados.
* Recuperação dos detalhes de um evento específico com base no ID fornecido.
* Atualização de um evento específico.
* Exclusão de um evento existente.
* Filtro dos eventos com base nos parâmetros fornecidos.

## Instalação

Para clonar e executar este aplicativo, você precisará do [Git](https://git-scm.com) e do [Maven](https://maven.apache.org/download.cgi) instalados em seu computador. Na sua linha de comando:


#### Clone o repositório
```bash
$ git clone https://github.com/FellipeToledo/Urban-Mobility-Management-System
```
#### Instale as dependências
```bash
$ mvn clean install
```
#### Execute a aplicação
```bash
$ mvn spring-boot:run
```


## Exemplos
### CRUD de Eventos (com JPA e Spring Data JPA)
- Criar Evento (POST)

    - Endpoint: /api/events
    - Método: POST
    - Descrição: Permite a criação de um novo evento no sistema. O evento é cadastrado no banco de dados com informações como tipo de incidente, severidade, status, localização, e impacto no tráfego.
    - Corpo da requisição:
```json
{
  "description": "Acidente: Colisão na Avenida Brasil",
  "startDateTime": "2024-06-12T21:47:00",
  "endDateTime": "2024-06-12T23:59:59",
  "location": "Avenida Brasil, 1234",
  "severity": "Medium",
  "status": "Closed",
  "impactTransit": "Grave"
  "roadBlock": true
}
```
> Resposta: Retorna o evento criado com seu ID gerado.<br/>
> Código de Resposta: 201 Created

- Buscar Todos os Eventos (GET)

    - Endpoint: /api/events
    - Método: GET
    - Descrição: Recupera todos os eventos cadastrados no sistema. Os eventos são retornados em formato de lista, com informações completas.
    - Resposta:
  
```json
[
  {
    "id": 1,
    "description": "Acidente: Colisão na Avenida Brasil",
    "registrationDateTime": <"data e hora do registro">,
    "startDateTime": "2024-06-12T21:47:00",
    "endDateTime": "2024-06-12T23:59:59",
    "location": "Avenida Brasil, 1234",
    "severity": "Medium",
    "status": "Closed",
    "impactTransit": "Grave"
    "roadBlock": true
  },
  ...
]
```
> Código de Resposta: 200 OK<br/>

- Buscar Evento por ID (GET)

    - Endpoint: /api/events/{id}
    - Método: GET
    - Descrição: Recupera os detalhes de um evento específico com base no ID fornecido. Retorna um erro 404 caso o evento não seja encontrado.
    - Parâmetro de URL: id - ID do evento a ser buscado.
    - Resposta:

```json
{
  "id": 1,
  "description": "Acidente: Colisão na Avenida Brasil",
  "registrationDateTime": <"data e hora do registro">,
  "startDateTime": "2024-06-12T21:47:00",
  "endDateTime": "2024-06-12T23:59:59",
  "location": "Avenida Brasil, 1234",
  "severity": "Medium",
  "status": "Closed",
  "impactTransit": "Grave"
  "roadBlock": true
}
```

> Código de Resposta: 200 OK<br/>
> 404 Not Found caso o evento não exista.

- Atualizar Evento (PUT)

    - Endpoint: /api/events/{id}
    - Método: PUT
    - Descrição: Atualiza um evento existente no sistema. É necessário fornecer o ID do evento na URL, e os dados no corpo da requisição serão usados para atualizar o evento.
    - Parâmetro de URL: id - ID do evento a ser atualizado.
    - Corpo da requisição:

```json
{
  "description": "Acidente: Colisão na Avenida Brasil",
  "registrationDateTime": <"data e hora do registro">,
  "startDateTime": "2024-06-12T21:47:00",
  "endDateTime": "2024-06-12T23:59:59",
  "location": "Avenida Brasil, 1234",
  "severity": "High",
  "status": "Closed",
  "impactTransit": "Grave"
  "roadBlock": true
}
```
> Resposta: Retorna o evento atualizado.<br/>
> Código de Resposta: 200 OK.<br/>
> 404 Not Found caso o evento não exista.

- Excluir Evento (DELETE)

    - Endpoint: /api/events/{id}
    - Método: DELETE
    - Descrição: Exclui um evento existente no sistema com base no ID fornecido. Retorna um erro 404 caso o evento não seja encontrado.
    - Parâmetro de URL: id - ID do evento a ser excluído.
> Código de Resposta: 204 No Content<br/>
> 404 Not Found caso o evento não exista.

:bulb: Funcionalidades Extras (opcional para refinar o CRUD)
- Filtros de Eventos por Severidade, Status ou Localização
    - Endpoint: /api/events?severidade=Alta&status=Em andamento
    - Método: GET
    - Descrição: Filtra os eventos com base em parâmetros como severidade, status ou localização. Facilita a consulta personalizada dos eventos que impactam a mobilidade urbana.

## 🛠 Tecnologias

As seguintes ferramentas foram usadas na construção do projeto:

- [Java 21](https://www.oracle.com/java/technologies/javase/jdk21-archive-downloads.html)
- [Apache Maven](https://maven.apache.org/)
- [Spring Data JPA](https://github.com/spring-projects/spring-data-jpa)
- [H2 Database](https://github.com/h2database/h2database)
- [Swagger UI](https://github.com/swagger-api/swagger-ui)
- [Actuator](https://github.com/spring-projects/spring-boot/tree/main/spring-boot-project/spring-boot-actuator)
- [Lombok](https://github.com/projectlombok/lombok)

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
