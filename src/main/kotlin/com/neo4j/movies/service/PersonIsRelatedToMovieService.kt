package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.PersonIsRelatedToMovie
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.RELATIONSHIP
import org.neo4j.driver.Session
import org.neo4j.driver.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class PersonIsRelatedToMovieService (private val tx: Transaction): PersonIsRelatedToMovie {
    override fun execute(input: PersonIsRelatedToMovie.Input): PersonIsRelatedToMovie.Output {
        LOG.info("Person is related to movie for: $input")
        val query = """
            MATCH ${PERSON.withProps(PERSON.NAME)} -[r]-> ${MOVIE.withProps(MOVIE.TITLE)} 
            WHERE type(r) = ${RELATIONSHIP.param}
            RETURN type(r)
        """.trimIndent()
        val params = mapOf(PERSON.NAME.fromValue(input.personName), MOVIE.TITLE.fromValue(input.movieName), RELATIONSHIP.fromValue(input.relationship.name))
        val isRelated = tx.run(query, params).hasNext()
        return PersonIsRelatedToMovie.Output(isRelated)
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(PersonIsRelatedToMovieService::class.java)
    }

}