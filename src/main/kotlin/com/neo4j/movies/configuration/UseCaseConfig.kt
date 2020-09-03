package com.neo4j.movies.configuration

import com.neo4j.movies.businessrule.provider.*
import com.neo4j.movies.businessrule.usecase.AddCastToMovieUseCase
import com.neo4j.movies.businessrule.usecase.GetMoviesCastUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class UseCaseConfig {

    @Bean
    fun getMoviesCastUseCase(@Autowired getPeopleRelatedToMovie: GetPeopleRelatedToMovie): GetMoviesCastUseCase = GetMoviesCastUseCase(getPeopleRelatedToMovie)

    @Bean
    fun addCastToMovieUseCase(
            @Autowired personIsRelatedToMovie: PersonIsRelatedToMovie,
            @Autowired getMovieByName: GetMovieByName,
            @Autowired getPersonByName: GetPersonByName,
            @Autowired createPerson: CreatePerson,
            @Autowired addRelationBetweenPersonAndMovie: AddRelationBetweenPersonAndMovie
    ): AddCastToMovieUseCase = AddCastToMovieUseCase(
            personIsRelatedToMovie,
            getMovieByName,
            getPersonByName,
            createPerson,
            addRelationBetweenPersonAndMovie)
}