package com.neo4j.movies.businessrule.provider

import com.neo4j.movies.businessrule.entity.Person

interface CreatePerson : Provider<CreatePerson.Input, Unit> {

    data class Input(val person: Person)
}