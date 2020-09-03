package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.PersonIsRelatedToMovie
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.RELATIONSHIP
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class PersonIsRelatedToMovieService (@Autowired private val driver: Driver): PersonIsRelatedToMovie {
    override fun execute(input: PersonIsRelatedToMovie.Input): PersonIsRelatedToMovie.Output = driver.session().readTransaction { tx ->
        val query = """
            MATCH (${PERSON} { ${PERSON.NAME.eq} } ) -[r]-> (${MOVIE} { ${MOVIE.TITLE.eq} }) 
            WHERE type(r) = ${RELATIONSHIP.param}
            RETURN type(r)
        """.trimIndent()
        val params = mapOf(PERSON.NAME.fromValue(input.personName), MOVIE.TITLE.fromValue(input.movieName), RELATIONSHIP.fromValue(input.relationship.name))
        val isRelated = tx.run(query, params).hasNext()
        PersonIsRelatedToMovie.Output(isRelated)
    }

}