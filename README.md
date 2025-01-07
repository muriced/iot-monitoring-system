# Getting Started

### Reference Documentation
For further reference, please consider the following sections:

* [Official Apache Maven documentation](https://maven.apache.org/guides/index.html)
* [Spring Boot Maven Plugin Reference Guide](https://docs.spring.io/spring-boot/3.4.1/maven-plugin)
* [Create an OCI image](https://docs.spring.io/spring-boot/3.4.1/maven-plugin/build-image.html)
* [Spring Web](https://docs.spring.io/spring-boot/3.4.1/reference/web/servlet.html)
* [Spring Data MongoDB](https://docs.spring.io/spring-boot/3.4.1/reference/data/nosql.html#data.nosql.mongodb)
* [Spring for Apache Kafka](https://docs.spring.io/spring-boot/3.4.1/reference/messaging/kafka.html)
* [WebSocket](https://docs.spring.io/spring-boot/3.4.1/reference/messaging/websockets.html)
* [Spring Reactive Web](https://docs.spring.io/spring-boot/3.4.1/reference/web/reactive.html)

### Guides
The following guides illustrate how to use some features concretely:

* [Building a RESTful Web Service](https://spring.io/guides/gs/rest-service/)
* [Serving Web Content with Spring MVC](https://spring.io/guides/gs/serving-web-content/)
* [Building REST services with Spring](https://spring.io/guides/tutorials/rest/)
* [Accessing Data with MongoDB](https://spring.io/guides/gs/accessing-data-mongodb/)
* [Using WebSocket to build an interactive web application](https://spring.io/guides/gs/messaging-stomp-websocket/)
* [Building a Reactive RESTful Web Service](https://spring.io/guides/gs/reactive-rest-service/)

### Maven Parent overrides

Due to Maven's design, elements are inherited from the parent POM to the project POM.
While most of the inheritance is fine, it also inherits unwanted elements like `<license>` and `<developers>` from the parent.
To prevent this, the project POM contains empty overrides for these elements.
If you manually switch to a different parent and actually want the inheritance, you need to remove those overrides.

### Explanation (PT-BR)

O sistema monitora dispositivos IoT, coletando dados em tempo real e armazenando-os no MongoDB. O Kafka é utilizado como um mecanismo de mensageria para garantir a escalabilidade e a entrega de mensagens.

Características principais:

-> Uso de Virtual Threads do Java 21
-> Tratamento de erros robusto
-> Logs adequados
-> Configuração de tópicos (mensageria)
-> Headers Kafka para metadados
-> Processamento assíncrono
-> WebSocket para notificações em tempo real
-> Segurança
-> Documentação API
-> CORS
-> Métricas
-> Logging

A estrutura Kafka implementada permite:

-> Processamento distribuído
-> Alta disponibilidade
-> Escalabilidade horizontal
-> Monitoramento em tempo real
-> Processamento assíncrono de eventos
-> Integração com WebSocket para updates em tempo real

O kafka/config/KafkaTopicConfig:
Cria e configura os tópicos Kafka, define número de partições, define políticas de retenção, define configurações específicas dos tópicos.

O config/KafkaConfig:
Configura as factories de Producer e Consumer, define configurações de serialização/deserialização, configura aspectos básicos de conexão e comportamento do Kafka.

O MongoDB permite:

-> Flexibilidade no schema
-> Queries ricas
-> Boa performance
-> Escalabilidade

O Java 21 permite:

-> Virtual Threads (Fibers) que introduziu as "fibras", que permitem a criação de milhares de threads leves, ideal para aplicações com alta concorrência, como o processamento de dados em tempo real de diversos dispositivos IoT
-> Melhorias no garbage collector e na alocação de memória que contribuem para um melhor desempenho e menor consumo de recursos

--------------------------------------------------------------

A estrutura segue boas práticas como:

-> Separação clara de responsabilidades
-> Organização por camadas
-> Modularização do código
-> Facilidade de navegação
-> Padrões de nomenclatura consistentes

Cada pacote tem uma responsabilidade específica:

-> config/ - Configurações do sistema
-> controller/ - APIs REST
-> domain/ - Classes de domínio e DTOs
-> repository/ - Acesso a dados
-> service/ - Lógica de negócios
-> kafka/ - Integração com Apacha Kafka
-> simulation/ - Classes de simulação de dispositivos IoT
-> resorces/ - Arquivos .yaml de perfis de configuração utilizados em aplicações Spring Boot para definir as propriedades e configurações da aplicação. 

Todos os (controllers) usam e seguem:

-> Injeção de dependência
-> Documentação OpenAPI/Swagger
-> Tratamento adequado de respostas HTTP
-> Validação de dados
-> Paginação quando apropriado
-> Tratamento de exceções
-> Endpoints RESTful
-> Princípio da Responsabilidade Única

Características importantes do domain/:

-> Uso de records do Java para imutabilidade
-> Validações com Bean Validation
-> Documentação MongoDB
-> DTOs separados para requests e responses
-> Value Objects para encapsular conceitos do domínio
-> Exceptions
-> Classes de (eventos) para comunicação assíncrona
-> Separa claramente os conceitos
-> Facilita a validação
-> Permite evolução do modelo
-> Mantém a coesão
-> Facilita a manutenção
-> Segue princípios DDD

Cada (repository):

-> Estende MongoRepository para operações básicas de CRUD
-> Usa @Repository para indicar que é um bean de repositório
-> Define métodos específicos usando convenção de nomes
-> Usa @Query para queries mais complexas
-> Inclui paginação quando necessário
-> Oferece flexibilidade nas consultas

Cada (service):

-> Usa @Service para indicar que é um bean de serviço
-> Usa @Transactional para operações que modificam os dados
-> Tem injeção de dependências com @RequiredArgsConstructor
-> Usa logging com @Slf4j
-> Implementa regras de negócio específicas (Princípio)
-> Coordena interações entre repositories e outros serviços

Características do pacote simulation/:

-> Simula dispositivos IoT reais
-> Gera leituras periódicas
-> Cria anomalias aleatórias
-> Usa scheduling do Spring
-> Configurável por profile
-> Gera diferentes tipos de métricas
-> Logging apropriado

O simulador é útil para:
Testes do sistema, demonstrações, desenvolvimento, testes de carga, validação de alertas, verificação de processamento em tempo real

Para ativar a simulação, você pode usar o profile "simulation" nos arquivos .yaml:
spring:
  profiles:
    active: simulation

Características do pacote resources/:

-> Configurações específicas por ambiente
-> Configurações de segurança apropriadas
-> Configurações de logging
-> Propriedades da aplicação
-> Recursos estáticos
-> Banners personalizados
-> Uso de variáveis de ambiente em produção

Você pode acessar a documentação Swagger em:

http://localhost:8080/swagger-ui.html
http://localhost:8080/v3/api-docs (formato JSON)
