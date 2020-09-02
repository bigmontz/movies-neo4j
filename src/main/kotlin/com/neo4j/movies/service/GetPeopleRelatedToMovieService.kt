package com.neo4j.movies.service

import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.entity.Relationship
import com.neo4j.movies.businessrule.provider.GetPeopleRelatedToMovie
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
                                MATCH (p:Person)-[r]->(m:Movie {title: $movieName})
                                WHERE type(r) IN $relationship
                                RETURN p.name as name, p.born as born, type(r) as relationship
                            """.trimIndent()

                val params = mapOf(
                        movieName.fromValue(input.movieName),
                        relationship.fromValue(input.relationships.map { it.name }))

                val actors = tx.run(query, params)
                        .list()
                        .map { record ->
                            Pair(
                                    record.get(relationship), Person(record.get(name), record.get(born)))
                        }

                return@readTransaction GetPeopleRelatedToMovie.Output(actors)
            }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(GetPeopleRelatedToMovieService::class.java)
        val relationship = EnumFieldDef("relationship", Relationship::valueOf)
        val movieName = StringFieldDef("movieName")
        val name = StringFieldDef("name")
        val born = IntFieldDef("born")
    }
}