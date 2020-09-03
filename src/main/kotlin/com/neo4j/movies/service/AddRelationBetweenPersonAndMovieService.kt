package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.AddRelationBetweenPersonAndMovie
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.RelationshipDef
import org.neo4j.driver.Transaction

class AddRelationBetweenPersonAndMovieService(private val tx: Transaction) : AddRelationBetweenPersonAndMovie {
    override fun execute(input: AddRelationBetweenPersonAndMovie.Input): Unit {
        val relationship = RelationshipDef(input.relationship.name, input.relationship.name.toLowerCase())
        val query = """
            MATCH ${PERSON.withProps(PERSON.NAME)}, ${MOVIE.withProps(MOVIE.TITLE)}
            CREATE (${PERSON.alias}) - [$relationship] -> (${MOVIE.alias})
        """.trimIndent()
        val params = mapOf(PERSON.NAME.fromValue(input.personName), MOVIE.TITLE.fromValue(input.movieName))
        tx.run(query, params)
    }
}