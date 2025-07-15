# Parallel Reading

## Book Service

The main microservice stores data about books, book pages, chapters

### PostgreSQL

**Used as a database in the book-service module**

```shell
docker run --name books-db -p 5433:5432 -e POSTGRES_DB=books-db -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password postgres:16
```

### Redis

**Used for caching**

```shell
docker run -d --name books-service-cache -p 6379:6379 -e REDIS_PASSWORD=password redis:latest --requirepass password
```

## Translation Service

Responsible for the translation, temporarily caches the result. Implements translation using the [Yandex Dictionary API](https://yandex.ru/dev/dictionary/).

Since the Yandex Dictionary is based on machine learning technologies, some words need to be reduced to their basic form.
To do this, the microservice uses [StanfordNLP](https://nlp.stanford.edu/) to find the lemma of a plural word, as well as regular past tense verbs.

### Redis

**Used for caching**

```shell
docker run -d --name translation-service-cache -p 6380:6379 -e REDIS_PASSWORD=password redis:latest --requirepass password
```

### .env

The environment configuration file, stores:

- YANDEX_DICT_API_KEY - API key for Yandex Dictionary can be obtained at https://yandex.ru/dev/dictionary/

## Dictionary Service

Responsible for managing user flashcards and vocabulary tracking. Keycloak id (sub) is used to identify users.

### PostgreSQL

**Used for store dictionary cards and users**

```shell
docker run --name dictionary-db -p 5434:5432 -e POSTGRES_DB=dictionary-db -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password postgres:16
```
## Security

**Keycloak for as an OAuth 2.0/OIDC server**

```shell
docker run --name keycloak -p 8082:8080 -e KEYCLOAK_ADMIN=admin -e KEYCLOAK_ADMIN_PASSWORD=admin -v ./config/keycloak/import:/opt/keycloak/data/import quay.io/keycloak/keycloak:26.2 start-dev --import-realm
```