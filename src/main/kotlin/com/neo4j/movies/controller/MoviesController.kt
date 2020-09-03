package com.neo4j.movies.controller

import com.neo4j.movies.businessrule.usecase.AddCastToMovieUseCase
import com.neo4j.movies.businessrule.usecase.GetMoviesCastUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*

@RestController()
class MoviesController(
        @Autowired private val getMoviesCastUseCase: GetMoviesCastUseCase,
        @Autowired private val addCastToMovieUseCase: AddCastToMovieUseCase) {

    @GetMapping("/movie/cast")
    fun getCastFormMovie(@RequestParam(value = "movieName", required = true) movieName: String): GetMoviesCastUseCase.Output {
        return getMoviesCastUseCase.execute(GetMoviesCastUseCase.Input(movieName))
    }

    @PutMapping("/movie/cast")
    fun putCastToMovie(@RequestBody(required = true) input: AddCastToMovieUseCase.Input): Unit {
        addCastToMovieUseCase.execute(input)
    }

}