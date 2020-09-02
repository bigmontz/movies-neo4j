package com.neo4j.movies.service

import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.entity.Relationship
import com.neo4j.movies.businessrule.provider.GetPeopleRelatedToMovie
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.stream.Collectors

@Service
class GetPeopleRelatedToMovieService(@Autowired private val driver: Driver) : GetPeopleRelatedToMovie {
    override fun execute(input: GetPeopleRelatedToMovie.Input): GetPeopleRelatedToMovie.Output {
        return driver.session().readTransaction { it ->
            val actors = it.run("MATCH (p:Person)-[r:ACTED_IN]->(m:Movie {title: \$movieName}) return p",
                    mapOf(Pair("movieName", input.movieName)))
                    .list()
                    .stream()
                    .map { record -> record.get("p").asNode() }
                    .map { node -> Pair(Relationship.ACTED_IN, Person(node.get("name").asString(), node.get("born").asInt())) }
                    .collect(Collectors.toList())
            return@readTransaction GetPeopleRelatedToMovie.Output(actors)
        }
    }
}