package com.neo4j.movies.service

import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.provider.GetPersonByName
import org.springframework.stereotype.Service

@Service
class GetPersonByNameService : GetPersonByName {
    override fun execute(input: GetPersonByName.Input): GetPersonByName.Output {
        return GetPersonByName.Output(Person(input.name, 199))
    }
}