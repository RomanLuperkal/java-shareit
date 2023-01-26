---

# *Java-shareit*

Описание проекта
-
Приложение предоставляющее возможность сдавать в аренду свои предметы и арендовать предметы у других пользователей.

Использованные технологии:
-

- Java 11, Maven, Spring-Boot, Hibernate, PostgreSQL, Lombok, Docker-compose, JPA, H2Database, WebClient

Функционал приложения:
-

1. ### Проект реализован по микро-сервисной архитектуре:
    * gateway - валидация входящих в запрос данных
    * server - обработка запроса и возвращение ответа

2. ### Основной функционал:

    * Создание\редактирование\получение\удаление пользователя
    * Создание\редактирование\получение\удаление предмета пользователем
    * Cоздание\удаление комментариев
    * Создание бронирования на определенную вещь\получение информации о бронировании предмета
    * Cоздание\получение информации о запросах на бронирование предмета

3. ### ER диаграмма базы данных приложения:
![ER_diagram_for_shareit](/assets/images/shareit-ER-diagramm.png)
Инструкция по запуску:

1. Для работы сервисов server и gateway необходима запущенная бд Postgres. 
2. С помощью pgAdmin4 необходимо создать базу данных postgreSQL _**shareit**_:
   * POSTGRES_USER = root
   * POSTGRES_PASSWORD = root
   * POSTGRES_DB = shareit

2. Для запуска проекта необходимы docker и docker-compose.
3. Команда "docker-compose up" запускает оба сервиса с бд
4. Для проверки функциональности и работоспособности приложения предусмотрены postman тесты:
   [shareit-tests](https://github.com/RomanLuperkal/java-shareit/blob/main/postman/ShareIt-test.json)  