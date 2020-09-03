package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.AddRelationBetweenPersonAndMovie
import org.springframework.stereotype.Service

@Service
class AddRelationBetweenPersonAndMovieService : AddRelationBetweenPersonAndMovie {
    override fun execute(input: AddRelationBetweenPersonAndMovie.Input) {

    }
}