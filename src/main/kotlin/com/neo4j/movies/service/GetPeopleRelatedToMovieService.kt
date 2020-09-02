package com.neo4j.movies.service

import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.entity.Relationship
import com.neo4j.movies.businessrule.provider.GetPeopleRelatedToMovie
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetPeopleRelatedToMovieService(@Autowired private val driver: Driver) : GetPeopleRelatedToMovie {
    override fun execute(input: GetPeopleRelatedToMovie.Input): GetPeopleRelatedToMovie.Output {
        return GetPeopleRelatedToMovie.Output(
                listOf(Pair(Relationship.ACTED_IN, Person(name="Antonio", born = 1986))))
    }
}