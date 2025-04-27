### PostgreSQL

**Used as a database in the book-service module**

```shell
docker run --name books-db -p 5433:5432 -e POSTGRES_DB=books-db -e POSTGRES_USER=username -e POSTGRES_PASSWORD=password postgres:16
```

### Redis

**Used for caching**

```shell
docker run -d --name redis-container -p 6379:6379 -e REDIS_PASSWORD=password redis:latest --requirepass password
```