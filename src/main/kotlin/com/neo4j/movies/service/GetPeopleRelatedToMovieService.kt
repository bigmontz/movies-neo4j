package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.GetPeopleRelatedToMovie
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.RELATIONSHIP
import com.neo4j.movies.service.database.get
import org.neo4j.driver.Driver
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetPeopleRelatedToMovieService(@Autowired private val driver: Driver) : GetPeopleRelatedToMovie {

    override fun execute(input: GetPeopleRelatedToMovie.Input): GetPeopleRelatedToMovie.Output =
            driver.session().readTransaction { tx ->
                LOG.info("Getting people related ${input.relationships} to the movie ${input.movieName}")

                val query = """
                                MATCH ($PERSON)-[r]->${MOVIE.withProps(MOVIE.TITLE)}
                                WHERE type(r) IN ${RELATIONSHIP.param}
                                RETURN ${PERSON.alias}, type(r) as relationship
                            """.trimIndent()

                val params = mapOf(
                        MOVIE.TITLE.fromValue(input.movieName),
                        RELATIONSHIP.fromValue(input.relationships.map { it.name }))

                val actors = tx.run(query, params)
                        .list()
                        .map { record ->
                            Pair(record.get(RELATIONSHIP), record.get(PERSON))
                        }

                return@readTransaction GetPeopleRelatedToMovie.Output(actors)
            }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(GetPeopleRelatedToMovieService::class.java)
    }
}