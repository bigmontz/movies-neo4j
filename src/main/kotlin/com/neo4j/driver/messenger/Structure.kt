package com.neo4j.driver.messenger

data class Structure(val type: Type, val data: List<Any>) {

    enum class Type {
        NODE
    }
}
