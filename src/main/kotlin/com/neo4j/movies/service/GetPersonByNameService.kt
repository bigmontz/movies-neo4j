package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.GetPersonByName
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.get
import org.neo4j.driver.Transaction

class GetPersonByNameService(private val tx: Transaction) : GetPersonByName {

    override fun execute(input: GetPersonByName.Input): GetPersonByName.Output {
        val query = """
            MATCH ${PERSON.withProps(PERSON.NAME)} 
            RETURN ${PERSON.alias}
        """.trimIndent()

        val param = mapOf(PERSON.NAME.fromValue(input.name))
        val person = tx.run(query, param).list().map { it.get(PERSON) }.firstOrNull()
        return GetPersonByName.Output(person)
    }

}