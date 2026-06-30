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




