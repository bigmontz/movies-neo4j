package com.neo4j.movies.controller

import com.neo4j.driver.Bolt
import com.neo4j.movies.businessrule.usecase.AddCastToMovieUseCase
import com.neo4j.movies.businessrule.usecase.GetMoviesCastUseCase
import com.neo4j.movies.configuration.UseCaseConfig
import com.neo4j.movies.service.database.sync
import com.neo4j.movies.service.database.syncReadTransaction
import com.neo4j.movies.service.database.syncWriteTransaction
import org.neo4j.driver.Driver
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.*
import java.net.Socket

@RestController()
class MoviesController(@Autowired private val driver: Driver, @Autowired private val useCaseConfig: UseCaseConfig) {

    @GetMapping("/movie/cast")
    fun getCastFormMovie(@RequestParam(value = "movieName", required = true) movieName: String): GetMoviesCastUseCase.Output = driver.syncReadTransaction { tx ->
        useCaseConfig.getMoviesCastUseCase(tx).execute(GetMoviesCastUseCase.Input(movieName))
    }


    @PutMapping("/movie/cast")
    fun putCastToMovie(@RequestBody(required = true) input: AddCastToMovieUseCase.Input): Unit = driver.syncWriteTransaction { tx ->
        useCaseConfig.addCastToMovieUseCase(tx).execute(input)
    }

    @GetMapping("test")
    fun test() = Bolt("localhost", 7687).use {
    }


}