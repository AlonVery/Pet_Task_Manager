## Верхнеуровневый вид (слойная + порты/адаптеры)

```
taskflow
 ├── domain
 │    ├── model
 │    ├── service
 │    ├── event
 │    └── repository   (интерфейсы)
 │
 ├── application
 │    ├── usecase
 │    ├── dto
 │    └── service
 │
 ├── web
 │    ├── controller
 │    ├── routing
 │    ├── request
 │    └── response
 │
 └── infrastructure
      ├── config
      ├── persistence
      │     ├── jdbc
      │     └── mapper
      ├── cache
      │     └── redis
      ├── messaging
      │     └── kafka
      ├── http
      │     └── jetty
      └── security
```

🧩 **Взаимодействие слоёв**
```
web  →  application  →  domain
 ↑         ↓            ↑
 |       infra ←────────┘
 |         (jdbc / redis / kafka)
 |
 Jetty (infra/http)
```
- **Domain** ничего не знает о инфраструктуре.
- **Application** обращается к домену и к интерфейсам репозиториев.
- **Infrastructure** реализует интерфейсы, обеспечивая связь с PostgreSQL / Redis / Kafka.
- **Web** вызывает application-слой и преобразует HTTP ↔ DTO.


# 🔎 **Описание уровней**

## **1. domain — чистый домен**

Никакой инфраструктуры, только бизнес-логика.
```
domain
 ├── model
 │     Board, Task, User, ValueObjects
 │
 ├── service
 │     Domain Services (например: TaskRulesService)
 │
 ├── event
 │     TaskCreatedEvent, TaskMovedEvent, ...
 │
 └── repository
       BoardRepository, TaskRepository, UserRepository (только интерфейсы)
```


## **2. application — логика приложения (use cases)**

Использует domain, но не знает ничего о web/JDBC/Redis.
```
application
 ├── usecase
 │     CreateTaskUseCase
 │     MoveTaskUseCase
 │     CreateBoardUseCase
 │     AuthenticateUserUseCase
 │
 ├── dto
 │     входные и выходные модели для use-case’ов
 │
 └── service
       Application services, фасады orchestration-логики
```


## **3. web — HTTP слой (Jetty + ручной REST)**

Маршрутизация, контроллеры, JSON.
```
web
 ├── controller
 │     BoardController
 │     TaskController
 │     AuthController
 │
 ├── routing
 │     Router, Route, Middleware
 │
 ├── request
 │     мапперы HTTP → DTO
 │
 └── response
       сериализация JSON → HTTP
```



## **4. infrastructure — низкоуровневые детали**

Всё, что зависит от технологий: JDBC, Redis, Kafka, Jetty, SQL, токены.
```
infrastructure
 ├── config
 │     DatabaseConfig, RedisConfig, KafkaConfig, JettyConfig
 │
 ├── persistence
 │     ├── jdbc
 │     │     BoardRepositoryJdbc
 │     │     TaskRepositoryJdbc
 │     │     UserRepositoryJdbc
 │     │
 │     └── mapper
 │           ResultSet → domain model
 │
 ├── cache
 │     └── redis
 │           RedisTokenStore
 │           BoardCacheRepository
 │
 ├── messaging
 │     └── kafka
 │           KafkaEventProducer
 │           ActivityLogConsumer
 │
 ├── http
 │     └── jetty
 │           JettyServer, JettyRequestAdapter
 │
 └── security
       TokenGenerator, TokenValidator
```
