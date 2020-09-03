package com.neo4j.movies.businessrule.provider

import com.neo4j.movies.businessrule.entity.Movie

interface GetMovieByName : Provider<GetMovieByName.Input, GetMovieByName.Output> {
    data class Input(val name: String)
    data class Output(val movie: Movie?)
}