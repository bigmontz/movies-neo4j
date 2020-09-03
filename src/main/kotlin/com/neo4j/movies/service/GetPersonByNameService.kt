package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.GetPersonByName
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.get
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetPersonByNameService(@Autowired val driver: Driver) : GetPersonByName {

    override fun execute(input: GetPersonByName.Input): GetPersonByName.Output = driver.session().readTransaction { tx ->
        val query = """
            MATCH (${PERSON} { ${PERSON.NAME.prop}})
            RETURN ${PERSON.alias}
        """.trimIndent()

        val param = mapOf(PERSON.NAME.fromValue(input.name))
        val person = tx.run(query, param).list().map { it.get(PERSON) }.firstOrNull()
        GetPersonByName.Output(person)
    }

}