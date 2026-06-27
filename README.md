# Projeto de Factory de Notificações

Bem-vindo ao projeto de Sistema de Notificações, desenvolvido utilizando boas práticas de engenharia de software com foco em manutenibilidade e escalabilidade.

## 🚀 Sobre o Projeto
Este é um microsserviço de notificações construído com **Java** e **Spring Boot**. O objetivo do projeto é gerenciar usuários e orquestrar o envio de notificações assíncronas através de diversos canais (E-mail, SMS, Push Notifications e WhatsApp). O projeto utiliza **PostgreSQL** como banco de dados e **RabbitMQ** para mensageria assíncrona.

## 🏗️ Arquitetura Escolhida
O projeto foi estruturado seguindo os princípios da **Clean Architecture** (Arquitetura Limpa), visando desaclopagem e alta coesão das regras de negócio. O código-fonte está dividido nas seguintes camadas principais:

- **Domain** (`/domain`): O coração da aplicação. Contém as entidades de negócio puras (ex: `User`, `Notification`), enumerações, além das interfaces de Casos de Uso, Portas (Gateways) de notificação e Repositórios de dados. Não possui nenhuma dependência externa severa como do Spring.
- **Application** (`/application`): Contém a implementação da lógica da aplicação e os Casos de Uso através de *Services*. A camada também gerencia os DTOs (Data Transfer Objects) e os Mappers responsáveis por transitar informações em conjunto com a próxima camada.
- **Infrastructure** (`/infrastructure`): Camada de interface externa. Aqui se concentram as tecnologias usadas: entidades e mapeadores do JPA, configurações e integrações de mensageria, implementações de comunicação com o Banco de Dados (Repositórios), implementações de integração (Gateways de envio de notificação) etc.
- **Presentation** (`/presentation`): A camada responsável pelas portas de entrada no sistema. Neste projeto se resume fundamentalmente a controladores REST (`NotificationController`, `UserController`) que recebem e respondem as requisições HTTP e tratamento global de exceções.

## 📐 Design Patterns Aplicados
Um dos principais padrões de projeto adotados nesta implementação é o **Factory Pattern** (Padrão de Fábrica), especificamente focado na estratégia de envio das notificações:

### Factory Method (NotificationSenderFactory)
O envio prático de uma notificação pode ocorrer por múltiplos canais — seja via SMS, E-mail, mensagens Push em dispositivos móveis, e WhatsApp.

- Ao longo da camada de *Infrastructure*, há implementações dedicadas para que cada tecnologia lide com seu próprio comportamento, separadas em objetos específicos (`EmailNotificationSender`, `PushNotificationSender`, `SmsNotificationSender`, `WhatsAppNotificationSender`).
- Para abster a camada da aplicação (serviço) de saber "como" instanciar ou qual classe exata usar, é utilizado um mecanismo na forma de `NotificationSenderFactory`. 
- Ao longo da validação da notificação que deve ser gerada e consumida, a Fábrica é injetada para que consiga construir/prover instancialmente o implementador correto de acordo com a regra de negócio/tipo selecionado, centralizando a lógica de determinação dessa dependência. Em complemento também temos a implementação via injeção de coleções do Spring para o mecanismo de *Factory*.

## 🛠️ Tecnologias Utilizadas
- **Java 17+** / **Spring Boot**
- **Clean Architecture** (estrutura de pacotes modelada no projeto)
- **PostgreSQL** para o armazenamento persistente.
- **RabbitMQ** (via Docker) para fila de processamento assíncrono.
- **JUnit / Mockito** para os testes automatizados da lógica que já estão reportados pelo JaCoCo e Surefire (`target/surefire-reports/*`).
- O ecossistema é suportado via contêineres e um orquestrador com a configuração já no local (`docker-compose.yml`).
