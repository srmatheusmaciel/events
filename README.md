# Events API

## Visão Geral

Events é uma API REST desenvolvida em Spring Boot que permite a criação e gerenciamento de eventos, oferecendo funcionalidades para convidar participantes através de links personalizados e acompanhar um ranking dos usuários que mais trouxeram novos participantes.

## Funcionalidades Principais

- **Criação e Consulta de Eventos**: Cadastre novos eventos com informações detalhadas e consulte-os por nome amigável (prettyName).
- **Sistema de Inscrições**: Permita que usuários se inscrevam em eventos.
- **Links de Convite Personalizados**: Gere links únicos para usuários compartilharem com potenciais participantes.
- **Sistema de Referência**: Rastreie quais usuários convidaram outros participantes.
- **Ranking de Indicações**: Visualize os usuários que mais trouxeram novos participantes para cada evento.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.4.2
- Spring Data JPA
- MySQL
- Lombok
- Maven

## Endpoints da API

### Eventos

- **Criar Evento**
  - `POST /events/`
  - Cria um novo evento e gera automaticamente um nome amigável (prettyName).

- **Listar Todos os Eventos**
  - `GET /events`
  - Retorna todos os eventos cadastrados.

- **Consultar Evento por Nome Amigável**
  - `GET /events/{prettyName}`
  - Retorna os detalhes de um evento específico pelo seu nome amigável.

### Inscrições

- **Criar Inscrição**
  - `POST /subscription/{prettyName}`
  - Inscreve um usuário em um evento.

- **Criar Inscrição com Indicação**
  - `POST /subscription/{prettyName}/{userId}`
  - Inscreve um usuário em um evento, registrando quem o indicou.

- **Ver Ranking Geral (Top 3)**
  - `GET /subscription/{prettyName}/ranking`
  - Retorna os três usuários que mais indicaram pessoas para o evento.

- **Ver Posição de Usuário no Ranking**
  - `GET /subscription/{prettyName}/ranking/{userId}`
  - Retorna a posição específica de um usuário no ranking de indicações.

## Modelos de Dados

### Evento (EventModel)
```
{
  "eventId": integer,
  "title": string,
  "prettyName": string,
  "location": string,
  "price": double,
  "startDate": date,
  "endDate": date,
  "startTime": time,
  "endTime": time
}
```

### Usuário (UserModel)
```
{
  "id": integer,
  "name": string,
  "email": string
}
```

### Inscrição (SubscriptionModel)
```
{
  "subscriptionNumber": integer,
  "event": EventModel,
  "subscriber": UserModel,
  "indication": UserModel (opcional)
}
```

## Regras de Negócio

- Cada evento tem um nome amigável (prettyName) gerado automaticamente a partir do título.
- Um usuário só pode se inscrever uma vez em cada evento.
- Um usuário pode se inscrever com ou sem indicação de outro usuário.
- O sistema rastreia todas as indicações para gerar o ranking.

## Como Executar

### Pré-requisitos
- JDK 21
- Maven
- MySQL

### Configuração do Banco de Dados
1. Crie um banco de dados MySQL
2. Configure as credenciais no arquivo `application.properties`

### Executando a Aplicação
```bash
# Clone o repositório
git clone https://github.com/srmatheusmaciel/events.git

# Entre no diretório
cd events

# Compile o projeto
mvn clean install

# Execute a aplicação
mvn spring-boot:run
```

## Exemplos de Uso

### Criando um Evento
```bash
curl -X POST http://localhost:8080/events/ \
  -H "Content-Type: application/json" \
  -d '{
    "title": "Workshop de Spring Boot",
    "location": "Centro de Convenções",
    "price": 50.0,
    "startDate": "2025-03-15",
    "endDate": "2025-03-16",
    "startTime": "09:00:00",
    "endTime": "18:00:00"
  }'
```

### Inscrevendo um Usuário com Indicação
```bash
curl -X POST http://localhost:8080/subscription/workshop-de-spring-boot/1 \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Maria Silva",
    "email": "maria@example.com"
  }'
```

### Consultando o Ranking de um Evento
```bash
curl -X GET http://localhost:8080/subscription/workshop-de-spring-boot/ranking # EXEMPLO
```

## Tratamento de Erros

A API retorna os seguintes códigos de erro:

- `404 Not Found` - Quando um evento ou usuário não é encontrado
- `409 Conflict` - Quando um usuário tenta se inscrever em um evento no qual já está inscrito
- `400 Bad Request` - Para solicitações inválidas

## Contribuindo

Para contribuir com este projeto:

1. Faça um fork do repositório
2. Crie uma branch para sua feature (`git checkout -b feature/nova-funcionalidade`)
3. Commit suas mudanças (`git commit -m 'Adiciona nova funcionalidade'`)
4. Push para a branch (`git push origin feature/nova-funcionalidade`)
5. Abra um Pull Request

## Licença

Este projeto está licenciado sob a [Licença MIT](LICENSE).
