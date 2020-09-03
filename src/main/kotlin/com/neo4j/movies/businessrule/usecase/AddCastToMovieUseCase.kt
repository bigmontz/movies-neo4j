package com.neo4j.movies.businessrule.usecase

import com.neo4j.movies.businessrule.entity.Job
import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.provider.*
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.RuntimeException

class AddCastToMovieUseCase(
        private val personIsRelatedToMovie: PersonIsRelatedToMovie,
        private val getMovieByName: GetMovieByName,
        private val getPersonByName: GetPersonByName,
        private val createPerson: CreatePerson,
        private val addRelationBetweenPersonAndMovie: AddRelationBetweenPersonAndMovie
) : UseCase<AddCastToMovieUseCase.Input, Unit> {

    override fun execute(input: Input) {
        LOG.info("Adding cast: $input")

        if(isPersonRelatedToMovie(input)) {
            LOG.info("Person is already relate to the movie, nothing to do")
            return
        }

        val movie = getMovieByName
                .execute(GetMovieByName.Input(input.movieName))
                .movie ?: throw RuntimeException("Movie \"${input.movieName}\" is not found")

        LOG.info("Got $movie")

        val person = getPersonByName
                .execute(GetPersonByName.Input(input.personName))
                .person?: createPerson(name = input.personName, born = input.personBorn!!)

        LOG.info("Got $person")

        addRelationBetweenPersonAndMovie
                .execute(AddRelationBetweenPersonAndMovie.Input(input.movieName, input.personName, input.job.relationship))

    }

    private fun isPersonRelatedToMovie(input: Input) : Boolean =
            personIsRelatedToMovie.execute(PersonIsRelatedToMovie.Input(input.movieName, input.personName, input.job.relationship)).isRelated

    private fun createPerson(name: String, born : Int) : Person {
        val person = Person(name, born)
        LOG.info("Creating person: $person")
        createPerson.execute(CreatePerson.Input(person))
        return person
    }


    data class Input(
            val movieName: String,
            val personName: String,
            val personBorn: Int?,
            val job: Job)

    companion object {
        val LOG: Logger = LoggerFactory.getLogger(AddCastToMovieUseCase::class.java)
    }
}