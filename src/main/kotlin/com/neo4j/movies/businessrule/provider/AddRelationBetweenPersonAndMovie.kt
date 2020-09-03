package com.neo4j.movies.businessrule.provider

import com.neo4j.movies.businessrule.entity.Relationship

interface AddRelationBetweenPersonAndMovie : Provider<AddRelationBetweenPersonAndMovie.Input, Unit> {
    data class Input(val movieName: String, val personName: String, val relationship: Relationship)
}