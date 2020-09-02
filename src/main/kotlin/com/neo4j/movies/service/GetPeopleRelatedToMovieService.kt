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
                LOG.info("Getting people related to the movie ${input.movieName}")
                val actors = tx.run(
                            "MATCH (p:Person)-[r]->(m:Movie {title: \$movieName}) " +
                                    "WHERE type(r) IN \$relationships " +
                                    "RETURN p.name as name, p.born as born, type(r) as relationship",
                            mapOf(Pair("movieName", input.movieName), Pair("relationships", input.relationships.map { it.name })))
                        .list()
                        .map { node -> Pair(
                                Relationship.valueOf(node.get("relationship").asString()),
                                Person(node.get("name").asString(), node.get("born").asInt())) }
                
                return@readTransaction GetPeopleRelatedToMovie.Output(actors)
            }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(GetPeopleRelatedToMovieService::class.java)
    }
}