package com.neo4j.movies.configuration

import com.neo4j.movies.businessrule.usecase.AddCastToMovieUseCase
import com.neo4j.movies.businessrule.usecase.GetMoviesCastUseCase
import com.neo4j.movies.service.*
import org.neo4j.driver.Transaction
import org.springframework.stereotype.Service

@Service
class UseCaseConfig {

    fun getMoviesCastUseCase(tx: Transaction): GetMoviesCastUseCase = GetMoviesCastUseCase(GetPeopleRelatedToMovieService(tx))

    fun addCastToMovieUseCase(tx: Transaction): AddCastToMovieUseCase = AddCastToMovieUseCase(
            PersonIsRelatedToMovieService(tx),
            GetMovieByNameService(tx),
            GetPersonByNameService(tx),
            CreatePersonService(tx),
            AddRelationBetweenPersonAndMovieService(tx))
}