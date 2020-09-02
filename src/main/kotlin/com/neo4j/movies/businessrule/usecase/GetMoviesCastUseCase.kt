package com.neo4j.movies.businessrule.usecase

import com.neo4j.movies.businessrule.entity.Job
import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.provider.GetPeopleRelatedToMovie
import java.util.stream.Collectors


class GetMoviesCastUseCase(private val getPeopleRelatedToMovie: GetPeopleRelatedToMovie)
    : UseCase<GetMoviesCastUseCase.Input, GetMoviesCastUseCase.Output> {

    override fun execute(input: Input): Output {
        val cast = getPeopleRelatedToMovie.execute(GetPeopleRelatedToMovie.Input(movieName = input.movieName, relationships = Job.CAST_RELATIONSHIPS))
                .relatedPeople.stream()
                .map { Pair(Job.fromRelationship(it.first)!!, it.second ) }
                .collect(Collectors.toList())

        return Output(cast);
    }

    data class Input(val movieName :String)
    data class Output(val cast: List<Pair<Job, Person>>)

}