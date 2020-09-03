package com.neo4j.movies.service

import com.neo4j.movies.businessrule.entity.Movie
import com.neo4j.movies.businessrule.provider.GetMovieByName
import org.springframework.stereotype.Service

@Service
class GetMovieByNameService : GetMovieByName {
    override fun execute(input: GetMovieByName.Input): GetMovieByName.Output {
        return GetMovieByName.Output(Movie(input.name))
    }
}