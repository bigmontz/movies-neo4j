# movies-neo4j

It's a simple api build on top of Neo4j Database and it's movie database sample. 

## Commands

Build:

```docker-compose build```

Run the project:

```docker-compose up -d neo4j application```

Upload dataset:

```docker-compose up dataset```

Stop the service

```docker-compose down```

Alternativetly, the project could runned using:

```docker-compose up -d neo4j``` to start the database
```./mvnw spring-boot:run``` to build and run the application

It's usefull for development.