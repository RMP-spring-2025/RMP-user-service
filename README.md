# User service

### Запуск БД:

- _Используем команду **docker compose up -d** (ключ -d для фонового режима). Она поднимает postgres в 
образе, а также создает таблицы._
- _Создание таблиц прописано в [create_table.sql](docker/postgres/init/create_tables.sql)_
- _Пароль и Юзер прописаны в [docker-compose.yml](docker-compose.yml)_
- _Далее подключаемся к бд любыми удобными средствами и взаимодействуем с таблицами_

