# Listener de Pedidos - RabbitMQ e MongoDB

Este projeto foi desenvolvido para criar um listener que monitora um tópico publicado no RabbitMQ. O listener consome mensagens relacionadas a pedidos, processa os dados recebidos e os persiste de forma eficiente no banco de dados MongoDB
## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada para o desenvolvimento do projeto.
- **Spring Framework**: Framework utilizado para a construção do servidor e do listener.
- **RabbitMQ**: Sistema de mensageria para publicação e consumo de mensagens.
- **MongoDB**: Banco de dados utilizado para persistir os dados dos pedidos.


## 🚀 Funcionalidades

- **Consumo de Mensagens**: O listener consome mensagens de um tópico publicado no RabbitMQ.
- **Persistência de Dados**: As mensagens consumidas, que contêm informações sobre pedidos, são salvas no banco de dados MongoDB.
- **Testes Automatizados**: O projeto inclui testes automatizados para garantir o correto funcionamento das funcionalidades.

### GET `/customers/{customerId}/orders`

Este endpoint permite buscar os pedidos associados a um consumidor específico, identificado pelo seu ID.

#### Parâmetros:

- **`customerId`** (PathVariable): O ID do consumidor cujos pedidos serão recuperados.
- **`page`** (RequestParam): O número da página a ser recuperada. O valor padrão é `0`.
- **`pageSize`** (RequestParam): O número de pedidos por página. O valor padrão é `10`.

#### Funcionamento:

- O método consulta todos os pedidos relacionados ao `customerId` fornecido, utilizando paginação para retornar uma quantidade controlada de resultados.
- Além dos pedidos, o endpoint também retorna o total de pedidos (`totalOnOrders`) associados ao consumidor.
- A resposta é encapsulada em um objeto `ApiResponse` que contém os pedidos, a quantidade total de pedidos e as informações de paginação.

#### Resposta:

A resposta é retornada como um `ResponseEntity<ApiResponse<OrderResponse>>`, contendo:

- **`totalOnOrders`**: Total de pedidos do consumidor.
- **`pageResponse.getContent()`**: A lista de pedidos na página atual.
- **`PaginationResponse.fromPage(pageResponse)`**: Informações de paginação para facilitar a navegação entre páginas.
- 
## 🧪 Testes

Os testes foram desenvolvidos utilizando o framework Jest e cobrem os seguintes cenários:

- **Consumo de Mensagens**: Testes para garantir que as mensagens são corretamente consumidas do RabbitMQ.
- **Persistência de Dados**: Testes para validar que os dados dos pedidos são salvos corretamente no MongoDB.
- **Erros e Exceções**: Testes para garantir o tratamento adequado de erros e exceções durante o processo de consumo e salvamento.


