# Parallel Reading

**Architectural scheme**

Not a very beautiful architectural scheme, I'll definitely make it more beautiful!!!

![Architecture scheme](https://github.com/user-attachments/assets/4820e342-1d32-4d19-8632-5ee481d9a0c6)

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

## Book Upload Service

Responsible for parsing book files, creating a match between the text and the translation of the book, and sending it to the book service. Kafka is used for asynchronous communication.

## Auth Service

Response for user registration, password reset etc

## Admin client

Admin web client for book management

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

## Minio

Storage images and files

```shell
docker run -p 9000:9000 -p 9001:9001 \
    -e "MINIO_ROOT_USER=admin" \
    -e "MINIO_ROOT_PASSWORD=password" \
    --name minio \
    -v minio_data:/data \
    minio/minio server /data --console-address ":9001"
```

## Kafka

```shell
docker run -d \
  --name kafka \
  -p 9092:9092 \
  -e KAFKA_NODE_ID=1 \
  -e KAFKA_PROCESS_ROLES=broker,controller \
  -e KAFKA_LISTENERS=PLAINTEXT://:9092,CONTROLLER://:9093 \
  -e KAFKA_ADVERTISED_LISTENERS=PLAINTEXT://host.docker.internal:9092 \
  -e KAFKA_CONTROLLER_LISTENER_NAMES=CONTROLLER \
  -e KAFKA_LISTENER_SECURITY_PROTOCOL_MAP=CONTROLLER:PLAINTEXT,PLAINTEXT:PLAINTEXT \
  -e KAFKA_CONTROLLER_QUORUM_VOTERS=1@localhost:9093 \
  -e KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR=1 \
  -e KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR=1 \
  -e KAFKA_TRANSACTION_STATE_LOG_MIN_ISR=1 \
  -e KAFKA_GROUP_INITIAL_REBALANCE_DELAY_MS=0 \
  apache/kafka:latest
```