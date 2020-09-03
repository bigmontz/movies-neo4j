package com.neo4j.movies.service

import com.neo4j.movies.businessrule.provider.PersonIsRelatedToMovie
import org.springframework.stereotype.Service

@Service
class PersonIsRelatedToMovieService : PersonIsRelatedToMovie {
    override fun execute(input: PersonIsRelatedToMovie.Input): PersonIsRelatedToMovie.Output {
        return PersonIsRelatedToMovie.Output(false)
    }
}