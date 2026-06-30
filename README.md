# City Guide — Путеводитель по городу

> **Фамилия Имя:** Королев Иван  
> **Тестовое задание:** Spring (Вариант 2)  
> **Репозиторий:** https://github.com/Wazewa/Guide-Project

---

## Описание проекта

**City Guide** — это веб-приложение-путеводитель, разработанное на стеке **Spring Boot + Angular**. Оно позволяет пользователям:

- Просматривать достопримечательности города с их описанием, адресами и фотографиями
- Искать места в указанном радиусе от заданной точки на карте
- Оставлять отзывы и оценки (от 1 до 5)
- Просматривать среднюю оценку каждой достопримечательности
- Регистрироваться и авторизоваться в системе

Проект демонстрирует навыки разработки **Fullstack-приложений** с использованием современных технологий: REST API, JPA, PostGIS, Spring Security, а также клиентской части на Angular с Material Design.

---

## Технологический стек

### Backend
- **Java 21** (Records, Pattern Matching)
- **Spring Boot 4.1** (REST API, Security, Data JPA)
- **Spring Security** (BCrypt, сессии, роли)
- **PostgreSQL 18** + **PostGIS** (геоданные, поиск по радиусу)
- **Flyway** (миграции схемы БД)
- **JUnit 5** + **Mockito** (unit-тесты)
- **Maven** (сборка)

### Frontend
- **Angular 19** (SPA, компоненты, сервисы, роутинг)
- **Angular Material** (UI-компоненты)
- **TypeScript** (строгая типизация)
- **Яндекс.Карты** (интерактивная карта, маркеры)
- **RxJS** (реактивное программирование)

### Инфраструктура
- **Git** (контроль версий)
- **IntelliJ IDEA** (среда разработки)

---

## Подготовительные действия

### 1. Установите необходимое ПО

- [Java 21+](https://adoptium.net/)
- [Node.js 22+](https://nodejs.org/)
- [PostgreSQL 18+](https://www.postgresql.org/download/)
- [PostGIS](https://postgis.net/install/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/download/) (или любая другая IDE)
- [Git](https://git-scm.com/downloads)

### 2. Клонируйте репозиторий

```bash
git clone https://github.com/ваш-проект.git
cd ваш-проект
```

### 3. Настройте базу данных

1. Создайте базу данных в PostgreSQL:
   ```sql
   CREATE DATABASE guide_db;
   ```
2. Установите расширение PostGIS:
   ```sql
   CREATE EXTENSION IF NOT EXISTS postgis;
   ```
3. В файле `backend/src/main/resources/application.properties` укажите свои данные для подключения:
   ```properties
   spring.datasource.url=jdbc:postgresql://localhost:5432/guide_db
   spring.datasource.username=postgres
   spring.datasource.password=ваш_пароль
   ```
### 4. Настройте переменные окружения для фронтенда (еще не добавлено)
Создайте файл `.env` в папке `frontend/`:
```env
NG_APP_YANDEX_MAPS_API_KEY=ваш_ключ_яндекс_карт
```
Ключ можно получить в кабинете разработчика Яндекс.Карт: https://developer.tech.yandex.ru/

### 5. Установите зависимости
   Backend:
   ```bash
   cd backend
   mvn clean install
   ```
   Frontend:
   ```bash
   cd frontend
   npm install
   ```
## Доступы (логины и пароли)

Для тестирования в системе созданы следующие пользователи:

| Роль | Email | Пароль |
|------|-------|--------|
| **Обычный пользователь** | `wazewa@test.ru` | `123456` |

> Учётная запись создастся автоматически при запуске Flyway(еще не добавлено). 

---

## Запуск проекта

### Способ 1: Запуск через IDE (рекомендуется)

1. Откройте папку `CityGuide` в **IntelliJ IDEA** как корневой проект.
2. **Запустите бэкенд:**
   - Выберите конфигурацию `GuideprojectApplication` → нажмите ▶️
   - Сервер поднимется на `http://localhost:8080`
3. **Запустите фронтенд:**
   - Откройте терминал в папке `frontend`
   - Выполните: `ng serve`
   - Приложение откроется на `http://localhost:4200`

### Способ 2: Запуск из терминала

**Backend:**
```bash
cd backend
./mvnw spring-boot:run
```

**Frontend(в другом терминале):**
```bash
cd frontend
ng serve
```

## Архитектура проекта

Проект построен по принципу **клиент-серверной архитектуры** с разделением на слои:

- **Controller** — REST-контроллеры (приём запросов, возврат DTO)
- **Service** — бизнес-логика (транзакции, проверки, вызовы репозиториев)
- **Repository** — доступ к базе данных (JPA + кастомные запросы)
- **DTO** — объекты передачи данных (безопасное разделение входящих и исходящих данных)
