package com.neo4j.movies.businessrule.provider

import com.neo4j.movies.businessrule.entity.Person

interface GetPersonByName : Provider<GetPersonByName.Input, GetPersonByName.Output> {

    data class Input(val name: String)
    data class Output(val person: Person?)
}