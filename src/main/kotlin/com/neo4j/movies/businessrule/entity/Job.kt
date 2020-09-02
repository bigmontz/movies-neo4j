package com.neo4j.movies.businessrule.entity

import java.util.*
import java.util.stream.Collectors

enum class Job(val relationship: Relationship) {
    ACTOR(Relationship.ACTED_IN),
    DIRECTOR(Relationship.DIRECTED),
    PRODUCER(Relationship.PRODUCED),
    WRITER(Relationship.WROTE);

    companion object {
        val CAST_RELATIONSHIPS : List<Relationship> by lazy {
            return@lazy Arrays.stream(values())
                    .map { it.relationship }
                    .distinct()
                    .collect(Collectors.toList())
        }

        fun fromRelationship(relationship: Relationship): Job? {
            return Arrays.stream(values())
                    .filter { it.relationship == relationship }
                    .findFirst()
                    .orElse(null)
        }
    }
}