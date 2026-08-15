#  Weather Aggregator Microservice
![Java](https://img.shields.io/badge/Java-21-orange)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-4.1.0-green)
![License](https://img.shields.io/badge/license-MIT-blue)

Микросервис для автоматического сбора, хранения и кеширования данных о погоде из внешнего API OpenWeatherMap.

##  Основные возможности

- **Автоматический сбор данных**: Шедулер (`@Scheduled`) опрашивает OpenWeatherMap API каждые 15 минут.
- **Хранение истории**: Все данные сохраняются в PostgreSQL с оптимизированными индексами для быстрых запросов.
- **Мгновенное кеширование**: Актуальная погода кешируется в Redis (TTL = 15 минут), что снижает нагрузку на базу данных и ускоряет ответы API.
- **Чистая архитектура**: Разделение на слои (Controller, Service, Repository, Mapper, Client) с использованием DTO.
- **Обработка ошибок**: Глобальный обработчик исключений и устойчивость к сбоям внешнего API (Graceful Degradation).
- **Готовая документация**: Интерактивная документация OpenAPI (Swagger UI).

##  Технологический стек

| Компонент            | Технология                          |
| :------------------- | :---------------------------------- |
| **Язык**             | Java 21 (LTS)                       |
| **Фреймворк**        | Spring Boot 4.1.0                   |
| **База данных**      | PostgreSQL 16                       |
| **Кеш**              | Redis 7                             |
| **ORM**              | Spring Data JPA (Hibernate)         |
| **Сборка**           | Maven                               |
| **Контейнеризация**  | Docker Compose                      |
| **Документация API** | Springdoc OpenAPI (Swagger UI)      |

##  Архитектура проекта

```text
redis-aggregator/
├── aggregator/                 # Основной Spring Boot сервис
│   ├── src/main/java/ru.redisproject.aggregator/
│   │   ├── client/             # Клиент для внешнего API (OpenWeatherMap)
│   │   ├── config/             # Конфигурации (Redis, RestClient)
│   │   ├── controller/         # REST контроллеры
│   │   ├── dto/                # Data Transfer Objects
│   │   ├── entity/             # JPA сущности
│   │   ├── exception/          # Глобальный обработчик ошибок
│   │   ├── mapper/             # Мапперы для преобразования DTO ↔ Entity
│   │   ├── repository/         # JPA репозитории
│   │   ├── scheduler/          # Шедулер для автообновления данных
│   │   └── service/            # Бизнес-логика (включая RedisCacheService)
│   └── src/main/resources/
│       └── application.yml     # Конфигурация приложения
├── docker-compose.yml          # Оркестрация PostgreSQL и Redis
├── README.md                   # Ты здесь
└── .gitignore                  # Игнорируемые файлы

```
##  Запуск проекта

### 1. Клонируй репозиторий
```bash
git clone https://github.com/D1n0zaur/redis-aggregator.git
cd redis-aggregator
```

### 2. Настрой переменные окружения

Создай файл `.env` в корневой папке и добавь свой API-ключ OpenWeatherMap:

```dotenv
OPENWEATHER_API_KEY=твой_апи_ключ
```
### 3. Запусти инфраструктуру (Docker Compose)

```bash
docker-compose up -d
```

Эта команда поднимет:
- **PostgreSQL** на порту `5432`
- **Redis** на порту `6379`

### 4. Собери и запусти приложение

```bash
cd aggregator
./mvnw spring-boot:run
```

**Приложение будет доступно по адресу: http://localhost:8080**

## Документация API

После запуска проекта интерактивная документация Swagger UI доступна по адресу:

 **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

**Основные эндпоинты:**

| Метод | Эндпоинт | Описание |
| :--- | :--- | :--- |
| `POST` | `/api/v1/weather` | Добавить погоду вручную |
| `GET` | `/api/v1/weather/{city}/current` | Получить текущую погоду (сначала из Redis, потом из БД) |
| `GET` | `/api/v1/weather/{city}/history` | Получить последние 5 записей из БД |

## Примеры запросов (cURL)

**Добавить запись о погоде (ручной ввод):**

```bash
curl -X POST http://localhost:8080/api/v1/weather \
  -H "Content-Type: application/json" \
  -d '{"city":"Moscow","temperature":23.5,"feelsLike":22.0,"humidity":65,"pressure":1015,"weatherCondition":"Clear"}'
```
**Получить текущую погоду**

```bash
curl http://localhost:8080/api/v1/weather/Moscow/current
```

**Получить историю**

```bash
curl http://localhost:8080/api/v1/weather/Moscow/history
```

## Планы на будущее (Roadmap)

- [ ] Написание модульных и интеграционных тестов
- [ ] Добавление поддержки WebSocket для уведомлений в реальном времени
- [ ] Интеграция с другим провайдером погоды (для fallback)
- [ ] Настройка метрик и мониторинга (Micrometer + Prometheus)

## Лицензия

MIT

## Контакты
- **GitHub:** [D1n0zaur](https://github.com/D1n0zaur)
- **Telegram:** @LokTarOgar812