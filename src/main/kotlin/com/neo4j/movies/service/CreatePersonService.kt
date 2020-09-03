package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.CreatePerson
import org.springframework.stereotype.Service

@Service
class CreatePersonService : CreatePerson {
    override fun execute(input: CreatePerson.Input) {

    }
}