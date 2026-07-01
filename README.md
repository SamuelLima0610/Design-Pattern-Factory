# Projeto de Abstract Factory de Notificações

Bem-vindo ao projeto de Sistema de Notificações, desenvolvido utilizando boas práticas de engenharia de software com foco em manutenibilidade e escalabilidade.

## 🚀 Sobre o Projeto

Este é um microsserviço de notificações construído com **Java** e **Spring Boot**. O objetivo do projeto é gerenciar usuários e orquestrar o envio de notificações assíncronas através de múltiplos canais (**E-mail**, **SMS**, **Push** e **WhatsApp**) com suporte a diferentes provedores por canal. O projeto utiliza **PostgreSQL** como banco de dados e **RabbitMQ** para mensageria assíncrona.

## 🏗️ Arquitetura Escolhida

O projeto segue os princípios da **Clean Architecture** (Arquitetura Limpa), garantindo desacoplamento e alta coesão das regras de negócio. O código-fonte está dividido nas seguintes camadas:

```
src/main/java/com/design/notification/
├── domain/                  ← Regras de negócio puras (sem dependências externas)
│   ├── entities/            ← User, Notification
│   ├── enums/               ← NotificationChannel, NotificationProvider, NotificationStatus
│   ├── gateways/            ← Interfaces: NotificationAbstractFactory, NotificationFactory,
│   │                           NotificationSender, NotificationFormatter, NotificationValidator,
│   │                           NotificationPublisher
│   ├── repositories/        ← Interfaces: NotificationRepository, UserRepository
│   └── usecases/            ← Interfaces de casos de uso (notification/ e user/)
│
├── application/             ← Implementação dos casos de uso e orquestração
│   ├── services/            ← NotificationService, UserService
│   ├── dtos/                ← DTOs de entrada/saída (notification/ e user/)
│   └── mappers/             ← NotificationDtoMapper, UserDtoMapper
│
├── infrastructure/          ← Detalhes tecnológicos e integrações externas
│   ├── entities/            ← NotificationEntity, UserEntity (JPA)
│   ├── mappers/             ← NotificationEntityMapper, UserEntityMapper
│   ├── repositories/        ← NotificationRepositoryImpl, UserRepositoryImpl + JPA interfaces
│   ├── messaging/           ← RabbitMQNotificationPublisher, NotificationConsumer
│   ├── config/              ← RabbitMQConfig
│   └── gateways/            ← Implementação do Abstract Factory (ver seção abaixo)
│       ├── factories/       ← Fábricas concretas por provedor
│       ├── senders/         ← Implementações de envio por provedor
│       ├── formatters/      ← Formatadores por canal
│       └── validators/      ← Validadores por canal
│
└── presentation/            ← Portas de entrada HTTP
    ├── controllers/         ← NotificationController, UserController
    └── exception/           ← GlobalExceptionHandler
```

## 📐 Design Pattern: Abstract Factory

O padrão central desta implementação é o **Abstract Factory**, aplicado para encapsular a criação de famílias de objetos relacionados ao envio de notificações — isolando completamente a camada de aplicação dos detalhes de cada provedor.

### O Problema

O envio de uma notificação envolve três responsabilidades distintas: **validar** os dados, **formatar** a mensagem e **enviar** pelo canal correto. Cada provedor (Gmail, Twilio, Firebase, etc.) possui suas próprias regras e SDKs para cada uma dessas etapas. Sem um padrão adequado, a lógica de seleção e instanciação desses objetos espalharia conhecimento de infraestrutura pela camada de aplicação.

### A Solução

**1. Interfaces da fábrica abstrata (Domain)**

```java
// Interface do produto abstrato — família de objetos para um provedor
public interface NotificationAbstractFactory {
    NotificationSender    createSender();
    NotificationFormatter createFormatter();
    NotificationValidator createValidator();
}

// Interface do produtor — retorna a fábrica correta pelo provedor
public interface NotificationFactory {
    NotificationAbstractFactory getFactory(NotificationProvider provider);
}
```

**2. Fábricas concretas por provedor (Infrastructure)**

Cada provedor possui sua própria fábrica concreta que sabe quais implementações instanciar:

| Fábrica Concreta | Canal | Sender | Formatter | Validator |
|---|---|---|---|---|
| `GmailNotificationFactory` | EMAIL | `GmailNotificationSender` | `EmailNotificationFormatter` | `EmailNotificationValidator` |
| `SendGridNotificationFactory` | EMAIL | `SendGridNotificationSender` | `EmailNotificationFormatter` | `EmailNotificationValidator` |
| `SesNotificationFactory` | EMAIL | `SesNotificationSender` | `EmailNotificationFormatter` | `EmailNotificationValidator` |
| `TwilioNotificationFactory` | SMS | `TwilioNotificationSender` | `SmsNotificationFormatter` | `SmsNotificationValidator` |
| `ZenviaNotificationFactory` | SMS | `ZenviaNotificationSender` | `SmsNotificationFormatter` | `SmsNotificationValidator` |
| `FirebaseNotificationFactory` | PUSH | `FirebaseNotificationSender` | `PushNotificationFormatter` | `PushNotificationValidator` |
| `OnesignalNotificationFactory` | PUSH | `OnesignalNotificationSender` | `PushNotificationFormatter` | `PushNotificationValidator` |
| `WhatsAppApiNotificationFactory` | WHATSAPP | `WhatsAppApiNotificationSender` | `WhatsAppNotificationFormatter` | `WhatsAppNotificationValidator` |

**3. Produtor das fábricas (NotificationFactoryProducer)**

`NotificationFactoryProducer` implementa `NotificationFactory` e mantém um `Map<NotificationProvider, NotificationAbstractFactory>` populado via injeção de dependência do Spring. Ao receber um `NotificationProvider`, retorna a fábrica concreta correspondente:

```java
NotificationAbstractFactory factory = notificationFactory.getFactory(provider);
factory.createValidator().validate(notification);
factory.createFormatter().format(notification);
factory.createSender().send(notification);
```

**4. Provedores disponíveis por canal**

```
EMAIL   → GMAIL | SENDGRID | SES
SMS     → TWILIO | ZENVIA
PUSH    → FIREBASE | ONESIGNAL
WHATSAPP → WHATSAPP_API
```

### Benefícios obtidos

- **Desacoplamento total**: a camada de aplicação só conhece as interfaces do domínio, nunca as classes concretas de infraestrutura.
- **Extensibilidade**: adicionar um novo provedor exige criar apenas uma nova fábrica concreta, sem modificar código existente (**OCP**).
- **Consistência**: cada fábrica garante que sender, formatter e validator sempre pertencem à mesma família coerente de implementações.
- **Testabilidade**: os serviços são testados com mocks das interfaces, sem nenhum acoplamento a SDKs externos.

## 🛠️ Tecnologias Utilizadas

- **Java 21** / **Spring Boot**
- **Clean Architecture** (estrutura de pacotes)
- **Abstract Factory** (padrão de projeto central)
- **PostgreSQL** para armazenamento persistente
- **RabbitMQ** (via Docker) para processamento assíncrono de notificações
- **JUnit 5 / Mockito** para testes automatizados
- **JaCoCo** para cobertura de testes (`target/site/jacoco/`)
- **Docker Compose** para orquestração local do ambiente (`docker-compose.yml`)
