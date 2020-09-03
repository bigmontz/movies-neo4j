package com.neo4j.movies.service.database

import com.neo4j.movies.businessrule.entity.Movie
import com.neo4j.movies.businessrule.entity.Person
import com.neo4j.movies.businessrule.entity.Relationship
import org.neo4j.driver.types.Node

object RELATIONSHIP : EnumFieldDef<Relationship>("relationship", Relationship::valueOf)

object PERSON : DataNodeDef<Person>("Person", "person") {
    object NAME : StringFieldDef("name")
    object BORN : IntFieldDef("born")

    override fun transform(node: Node): Person {
        return Person(name = node.get(NAME), born = node.get(BORN))
    }
}

object MOVIE : DataNodeDef<Movie>("Movie", "movie") {
    object TITLE :  StringFieldDef("title")

    override fun transform(node: Node): Movie {
        return Movie(name = node.get(TITLE))
    }
}