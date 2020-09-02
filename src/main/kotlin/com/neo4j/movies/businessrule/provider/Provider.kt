package com.neo4j.movies.businessrule.provider

interface Provider<Input, Output> {

    fun execute(input: Input): Output
}