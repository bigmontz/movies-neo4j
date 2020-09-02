package com.neo4j.movies.service

import org.neo4j.driver.Record
import org.neo4j.driver.Value

open class FieldDef(val name: String) {

    fun fromValue(value: Any) : Pair<String, Any> = Pair(name, value)

    override fun toString(): String {
        return "\$$name"
    }
}

class StringFieldDef(name: String) : FieldDef(name)
class IntFieldDef(name: String) : FieldDef(name)
class EnumFieldDef<E : Enum<E>>(name: String, val valueOf: (String) -> E): FieldDef(name)

fun Record.get(fieldDef: FieldDef): Value = this.get(fieldDef.name)
fun Record.get(fieldDef: StringFieldDef): String = this.get(fieldDef.name).asString()
fun Record.get(fieldDef: IntFieldDef): Int = this.get(fieldDef.name).asInt()
fun <E : Enum<E>> Record.get(fieldDef: EnumFieldDef<E>): E = fieldDef.valueOf(this.get(fieldDef.name).asString())