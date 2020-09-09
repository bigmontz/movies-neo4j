# movies-neo4j

It's a simple api build on top of Neo4j Database, and it's movie database sample. 

The application uses the `neo4j-java-driver-spring-boot-starter` to manager the connection with the
`database` by default. Thus, it uses the official driver. 

However, an alternative version the driver implemented under the package 
 `com.neo4j.driver` could be used by setting the environment variable 
 `NEO4J_EXPERIMENTAL` to `true`. This version of the drivers doesn't fulfil all the 
 capabilities of the official driver, but it implements part of the interfaces:
 - Transaction
 - Result
 - Record
 - Value
 - Node
 
Because of this, the swapping between the two implementations only impacts
the configuration class `TransacationFactoryConfig`. 

PS: Probably there are some corner cases which the new alternative driver
will not handle well because of some unhandled packing and unpacking of data or
something like that. 

WARNING: The alternative version of the driver is an exercise to understand how 
the Bolt protocol works, and it should be not used in production code


## Commands

Build:

```docker-compose build```

Run the project without upload dataset:

```docker-compose up -d neo4j application```

Upload dataset:

```docker-compose up dataset```

Run the project and upload dataset:

```docker-compose -f docker-compose.yml -f docker-compose.dataset.yml up -d```

To enable the experimental transaction, you should pass also `-f docker-compose.experimental.yml` to the `docker-compose up` command

Stop the service

```docker-compose down```

Alternatively, the project could run using:

```docker-compose up -d neo4j``` to start the database
```./mvnw spring-boot:run``` to build and run the application

It's useful for development.