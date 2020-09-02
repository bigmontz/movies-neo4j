package com.neo4j.movies.businessrule.usecase

interface UseCase<Input, Output> {

    fun execute(input: Input): Output
}