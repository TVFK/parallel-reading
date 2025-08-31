[![en](https://img.shields.io/badge/lang-en-red.svg)](README.md)
[![ru](https://img.shields.io/badge/lang-ru-green.svg)](.github/README.ru.md)
# Parallel Reading

A platform for parallel reading of books in foreign languages with an integrated dictionary and flashcard system

## Table of Contents
- [Architecture](#architecture)
    - [Services](#services)
    - [Infrastructure](#infrastructure)
- [Quick Start using Docker Compose](#quick-start-using-docker-compose)

## Architecture

Not a very beautiful architectural scheme, I'll definitely make it more beautiful!!!

![Architecture scheme](https://github.com/user-attachments/assets/98afd7c0-d0e9-4588-88cf-d0a968c6b844)

### Services

The system consists of the following microservices:

1) **Book Service** - manages books, chapters, and pages
2) **Translation Service** - translates words via the  [Yandex Dictionary API](https://yandex.ru/dev/dictionary/). Using [StanfordNLP](https://nlp.stanford.edu/) for lemmatization
3) **Dictionary Service** - manages user flashcards and vocabulary, implements the SuperMemo 2 algorithm for interval repetition
4) **Book Upload Service** - parses book texts using [Apache OpenNLP](https://opennlp.apache.org/)
5) **Auth Service** - user registration, password reset, etc
6) **Admin Client** - web interface for administrators

**Frontend app** (Vue.js 3, Typescript, Vite, TailWind): https://github.com/TVFK/parallel-reading-frontend

### Infrastructure

1) **PostgreSQL** - main data storage
2) **Redis** - caching frequently requested data
3) **Kafka** - asynchronous communication
4) **MinIO** - stores book covers and texts
5) **Keycloak** - OAuth 2.0/OIDC authentication
6) **Nginx** - reverse proxy and static content distribution

## Quick Start using Docker Compose

1. **Clone the repository**

```shell
git clone https://github.com/TVFK/parallel-reading-frontend.git
```

2. **Start all services**

```shell
docker-compose up -d --build
```
The application will be available at: http://localhost

### Environment variables

Translation service requires:
```env
YANDEX_DICT_API_KEY=your_yandex_dictionary_key
```
API key for Yandex Dictionary can be obtained at https://yandex.ru/dev/dictionary/