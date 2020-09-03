package com.neo4j.movies.businessrule.provider

import com.neo4j.movies.businessrule.entity.Relationship

interface PersonIsRelatedToMovie : Provider<PersonIsRelatedToMovie.Input, PersonIsRelatedToMovie.Output>  {

    data class Input(val movieName: String, val personName: String, val relationship: Relationship)
    data class Output(val isRelated: Boolean)
}