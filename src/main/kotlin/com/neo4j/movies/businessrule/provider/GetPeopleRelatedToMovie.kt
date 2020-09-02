package com.neo4j.movies.businessrule.provider

import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.entity.Relationship

interface GetPeopleRelatedToMovie: Provider<GetPeopleRelatedToMovie.Input, GetPeopleRelatedToMovie.Output> {

    data class Input(val movieName: String, val relationships: List<Relationship>)
    data class Output(val relatedPeople: List<Pair<Relationship, Person>>)
}