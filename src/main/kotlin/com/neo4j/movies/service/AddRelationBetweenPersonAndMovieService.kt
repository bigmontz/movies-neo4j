package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.AddRelationBetweenPersonAndMovie
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.RelationshipDef
import org.neo4j.driver.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class AddRelationBetweenPersonAndMovieService(private val tx: Transaction) : AddRelationBetweenPersonAndMovie {
    override fun execute(input: AddRelationBetweenPersonAndMovie.Input): Unit {
        LOG.info("Add Relation between Person and Movie for: $input")
        val relationship = RelationshipDef(input.relationship.name, input.relationship.name.toLowerCase())
        val query = """
            MATCH ${PERSON.withProps(PERSON.NAME)}, ${MOVIE.withProps(MOVIE.TITLE)}
            CREATE (${PERSON.alias}) - [$relationship] -> (${MOVIE.alias})
        """.trimIndent()
        val params = mapOf(PERSON.NAME.fromValue(input.personName), MOVIE.TITLE.fromValue(input.movieName))
        tx.run(query, params)
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(AddRelationBetweenPersonAndMovieService::class.java)
    }
}