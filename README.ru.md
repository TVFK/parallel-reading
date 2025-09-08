[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-green.svg)](README.ru.md)
# Parallel Reading

## Содержание
- [Архитектура](#архитектура)
    - [Сервисы](#сервисы)
    - [Инфраструктура](#инфраструктура)
- [Быстрый старт с Docker Compose](#быстрый-старт-с-docker-compose)

Платформа для параллельного чтения книг на иностранных языках со встроенным словарем и системой флэш-карт

## Архитектура

Не очень красивая архитектурная схема, но я обязательно сделаю её красивее!!!

![Architecture scheme](https://github.com/user-attachments/assets/f1fd5b71-e1a4-4773-8186-64231b27cbe1)

### Сервисы

Система состоит из следующих микросервисов:

1) **Book Service** - управление книгами, главами и страницами книг
2) **Translation Service** - перевод слов с помощью [Yandex Dictionary API](https://yandex.ru/dev/dictionary/). Использует [StanfordNLP](https://nlp.stanford.edu/) для лемматизации слов
3) **Dictionary Service** - управление пользовательскими флэшкартами и словарём, реализует алгоритм SuperMemo 2 для интервального повторения
4) **Book Upload Service** - парсинг текста книг с помощью [Apache OpenNLP](https://opennlp.apache.org/)
5) **Auth Service** - регистрацию пользователей, сброс пароля и т.д.
6) **Admin Client** - веб-интерфейс для администраторов

**Frontend app** (Vue.js 3, Typescript, Vite, TailWind): https://github.com/TVFK/parallel-reading-frontend

### Инфраструктура

1) **PostgreSQL** - основное хранилище данных 
2) **Redis** - кэширование часто запрашиваемых данных
3) **Kafka** - асинхронная коммуникацию и event-sourcing
4) **MinIO** - хранит обложки книги и текста книги
5) **Keycloak** - OAuth 2.0/OIDC аутентификация
6) **Nginx** - обратный прокси и раздача статического контента

## Быстрый старт с Docker Compose

1. **Клонирование репозитория**

```shell
git clone https://github.com/TVFK/parallel-reading.git
```
2. **Сбор проекта**

```shell
mvn clean package -DskipTests
```

3. **Запуск всех сервисов**

```shell
docker-compose up -d --build
```
Приложение будет доступно по адресу: http://localhost

Интерфейс админа: http://localhost:8083

Интерфейс MinIO: http://localhost:9090

Интерфейс Keycloak: http://localhost:8082

### Переменные окружения

Translation service необходимо:
```env
YANDEX_DICT_API_KEY=your_yandex_dictionary_key
```
API ключ для Yandex Dictionary можно получить по этому адресу https://yandex.ru/dev/dictionary/