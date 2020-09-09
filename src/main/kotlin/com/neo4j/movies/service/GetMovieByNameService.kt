package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.GetMovieByName
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.get
import org.neo4j.driver.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory

class GetMovieByNameService (private val tx: Transaction) : GetMovieByName {
    override fun execute(input: GetMovieByName.Input): GetMovieByName.Output {
        LOG.info("Get Movie By Name for: $input")
        val query = """
            MATCH ${MOVIE.withProps(MOVIE.TITLE)} 
            RETURN ${MOVIE.alias}
        """.trimIndent()
        val params = mapOf(MOVIE.TITLE.fromValue(input.name))
        val movie = tx.run(query, params).list().map { it.get(MOVIE) }.firstOrNull()
        return GetMovieByName.Output(movie)
    }

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(GetMovieByNameService::class.java)
    }

}