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

    * Создавать\редактировать\получать\удалять пользователя
    * Создавать\редактировать\получать\удалять предмет пользователем
    * создавать\удалять комментарии
    * Создавать брони на определенную вещь\получать информацию о бронировании предмета
    * создавать\получать информацию о запросах на бронирование предмета

3. ### ER диаграмма базы данных приложения:
![ER_diagram_for_filmorate](/assets/images/shareit-ER diagramm.png)