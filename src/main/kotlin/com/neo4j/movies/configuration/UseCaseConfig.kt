package com.neo4j.movies.configuration

import com.neo4j.movies.businessrule.provider.GetPeopleRelatedToMovie
import com.neo4j.movies.businessrule.usecase.GetMoviesCastUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun getMoviesCastUseCase(@Autowired getPeopleRelatedToMovie: GetPeopleRelatedToMovie): GetMoviesCastUseCase
        = GetMoviesCastUseCase(getPeopleRelatedToMovie)
}