package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.CreatePerson
import com.neo4j.movies.service.database.PERSON
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CreatePersonService(@Autowired private val driver: Driver) : CreatePerson {
    override fun execute(input: CreatePerson.Input): Unit = driver.session().writeTransaction { tx ->
        val query = """
            CREATE ${PERSON.withProps(PERSON.NAME, PERSON.BORN)} 
        """.trimIndent()
        val param = mapOf(PERSON.NAME.fromValue(input.person.name), PERSON.BORN.fromValue(input.person.born))
        tx.run(query, param)
    }
}