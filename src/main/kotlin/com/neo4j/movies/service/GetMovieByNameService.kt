package com.neo4j.movies.service

import com.neo4j.movies.businessrule.entity.Movie
import com.neo4j.movies.businessrule.provider.GetMovieByName
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class GetMovieByNameService (@Autowired private val driver: Driver) : GetMovieByName {
    override fun execute(input: GetMovieByName.Input): GetMovieByName.Output {
        val query = """
            MATCH (movie:Movie {title: "The Matrix Reloaded"}) 
            RETURN movie
        """.trimIndent()
        return GetMovieByName.Output(Movie(input.name))
    }
}