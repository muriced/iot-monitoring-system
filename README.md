# 📱 IoT Monitoring System

Monitore dispositivos IoT em tempo real, coletando dados, processando eventos com *Apache Kafka* e armazenando informações no *MongoDB. Aproveite o poder do **Java 21* com *Virtual Threads* para alta performance e escalabilidade. 🔥

---

## 🚀 *Getting Started*

### 📙 *Reference Documentation*
Consulte as documentações oficiais e guias para entender melhor os recursos utilizados:

- [📦 Apache Maven Documentation](https://maven.apache.org/guides/)
- [🚀 Spring Boot Maven Plugin Guide](https://docs.spring.io/spring-boot/docs/current/maven-plugin/)
- [🐳 Creating an OCI Image](https://spring.io/guides/topicals/spring-boot-docker/)
- *Spring Framework*:
  - [🌐 Spring Web](https://spring.io/guides/gs/rest-service/)
  - [🌿 Spring Data MongoDB](https://spring.io/projects/spring-data-mongodb)
  - [📮 Spring for Apache Kafka](https://spring.io/projects/spring-kafka)
  - [🔗 WebSocket](https://spring.io/guides/gs/messaging-stomp-websocket/)
  - [🌊 Spring Reactive Web](https://spring.io/guides/gs/reactive-rest-service/)

### 📋 *Guides*
Práticas e guias para uso dos recursos principais:
- Criando serviços RESTful
- Acessando dados no MongoDB
- Construindo aplicações reativas
- Usando WebSocket para interatividade em tempo real

---

## 🔍 *Features*

### *📦 Backend Core*
- *Java 21*:
  - *Virtual Threads* para alta concorrência
  - Melhorias no Garbage Collector para performance
- *Spring Boot*:
  - APIs REST com documentação Swagger
  - Processamento assíncrono com Kafka
  - WebSocket para notificações em tempo real
- *MongoDB*:
  - Flexibilidade no schema e escalabilidade

### *📱 Kafka Integration*
- Configuração de tópicos e policies de retenção
- Processamento distribuído e escalável
- Integração com WebSocket para updates em tempo real

### *🛠 Arquitetura Modular*
- Camadas bem definidas:
  - *Config*: Configurações do sistema
  - *Controller*: APIs REST
  - *Domain*: Modelos, DTOs e validações
  - *Repository*: Acesso a dados
  - *Service*: Lógica de negócios
  - *Simulation*: Simulação de dispositivos IoT

---

## 🛠️ *Estrutura e Boas Práticas*

### 🧬 *Arquitetura DDD*
- *Domain Layer*:
  - Uso de records para imutabilidade
  - Validações com Bean Validation
  - Value Objects para encapsular conceitos
- *Repositories*:
  - Operações CRUD com MongoRepository
  - Queries customizadas com @Query
- *Services*:
  - Uso de @Transactional e logging com @Slf4j
  - Princípio da Responsabilidade Única (SRP)

### 🧮 *Simulation Package*
- Simula dispositivos IoT reais:
  - Geração de métricas
  - Configurável por profiles
  - Testes de carga e validação de alertas

### 📂 *Resources*
- Configurações específicas por ambiente
- Uso de variáveis de ambiente
- Configuração de segurança e logging
- Documentação OpenAPI disponível:
  - [Swagger UI](http://localhost:8080/swagger-ui.html)
  - [OpenAPI JSON](http://localhost:8080/v3/api-docs)

---

## 🌟 *Como Rodar o Projeto*

1. Clone este repositório:
   bash
   git clone https://github.com/muriced/iot-monitoring-system.git
   
2. Navegue até o diretório:
   bash
   cd projeto-iot
   
3. Compile e rode:
   bash
   mvn clean install
   mvn spring-boot:run
   

4. Ative o *profile de simulação* (opcional):
   yaml
   spring:
     profiles:
       active: simulation
   

---

## 📊 *Principais Tecnologias*
![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=spring-boot&logoColor=white)
![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=for-the-badge&logo=mongodb&logoColor=white)
![Kafka](https://img.shields.io/badge/Apache%20Kafka-231F20?style=for-the-badge&logo=apache-kafka&logoColor=white)
![WebSocket](https://img.shields.io/badge/WebSocket-0088CC?style=for-the-badge&logo=websocket&logoColor=white)

---

## 📝 *Contribuição*
Contribuições são bem-vindas! Siga os passos:
1. Faça um fork do repositório
2. Crie uma branch:
   bash
   git checkout -b feature/nova-funcionalidade
   
3. Submeta seu PR 🎉

---

💡 *Dica*: Sempre consulte os logs gerados para validar as configurações e monitorar o desempenho.

---