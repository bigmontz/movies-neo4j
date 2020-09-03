package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.GetMovieByName
import com.neo4j.movies.service.database.MOVIE
import com.neo4j.movies.service.database.get
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetMovieByNameService (@Autowired private val driver: Driver) : GetMovieByName {
    override fun execute(input: GetMovieByName.Input): GetMovieByName.Output = driver.session().readTransaction { tx ->
        val query = """
            MATCH ${MOVIE.withProps(MOVIE.TITLE)} 
            RETURN ${MOVIE.alias}
        """.trimIndent()
        val params = mapOf(MOVIE.TITLE.fromValue(input.name))
        val movie = tx.run(query, params).list().map { it.get(MOVIE) }.firstOrNull()
        GetMovieByName.Output(movie)
    }

}