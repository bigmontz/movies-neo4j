package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.GetPersonByName
import com.neo4j.movies.service.database.PERSON
import com.neo4j.movies.service.database.get
import org.neo4j.driver.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GetPersonByNameService(private val tx: Transaction) : GetPersonByName {

    override fun execute(input: GetPersonByName.Input): GetPersonByName.Output {
        LOG.info("Get Person By Name for: $input")
        val query = """
            MATCH ${PERSON.withProps(PERSON.NAME)} 
            RETURN ${PERSON.alias}
        """.trimIndent()

        val param = mapOf(PERSON.NAME.fromValue(input.name))
        val person = tx.run(query, param).list().map { it.get(PERSON) }.firstOrNull()
        return GetPersonByName.Output(person)
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(GetPersonByNameService::class.java)
    }

}