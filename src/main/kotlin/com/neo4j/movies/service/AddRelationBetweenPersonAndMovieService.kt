package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.AddRelationBetweenPersonAndMovie
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.RelationshipDef
import com.neo4j.movies.service.database.syncWriteTransaction
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class AddRelationBetweenPersonAndMovieService(@Autowired private val driver: Driver) : AddRelationBetweenPersonAndMovie {
    override fun execute(input: AddRelationBetweenPersonAndMovie.Input): Unit = driver.syncWriteTransaction {  tx ->
        val relationship = RelationshipDef(input.relationship.name, input.relationship.name.toLowerCase())
        val query = """
            MATCH ${PERSON.withProps(PERSON.NAME)}, ${MOVIE.withProps(MOVIE.TITLE)}
            CREATE (${PERSON.alias}) - [$relationship] -> (${MOVIE.alias})
        """.trimIndent()
        val params = mapOf(PERSON.NAME.fromValue(input.personName), MOVIE.TITLE.fromValue(input.movieName))
        tx.run(query, params)
    }
}