package com.neo4j.movies.service.database

import com.neo4j.movies.businessrule.entity.Relationship

object RELATIONSHIP : EnumFieldDef<Relationship>("relationship", Relationship::valueOf)
object MOVIE_TITLE :  StringFieldDef("title")
object PERSON_NAME : StringFieldDef("name")
object PERSON_BORN : IntFieldDef("born")

object PERSON : NodeDef("Person", "person")
object MOVIE : NodeDef("Movie", "movie")