package com.neo4j.movies.controller

import com.neo4j.movies.businessrule.usecase.GetMoviesCastUseCase
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController()
class MoviesController(@Autowired private val getMoviesCastUseCase: GetMoviesCastUseCase) {

    @GetMapping("/movie/cast")
    fun getCastFormMovie(@RequestParam(value = "movieName", required = true) movieName: String): GetMoviesCastUseCase.Output {
        return getMoviesCastUseCase.execute(GetMoviesCastUseCase.Input(movieName))
    }
}