package com.neo4j.driver

import org.neo4j.driver.Value
import org.neo4j.driver.types.Node
import java.lang.IllegalArgumentException
import java.util.function.Function

class BoltNode(data: List<Any>) : Node {
    private val id: Int = when(data[0]) {
        is Byte -> (data[0] as Byte).toInt()
        is Short -> (data[0] as Short).toInt()
        is Int -> (data[0] as Int)
        else -> throw IllegalArgumentException("Value ${data[0]} is not a valid integer")
    }
    private val labels: List<String> = data[1] as List<String>
    private val properties: Map<String, Any> = data[2] as Map<String, Any>


    override fun get(label: String?): Value {
        if(!properties.containsKey(label))
            throw IllegalArgumentException("Label $label doesn't exist")
        return BoltValue(properties[label]!!)
    }

    override fun keys(): MutableIterable<String> {
        TODO("Not yet implemented")
    }

    override fun containsKey(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun values(): MutableIterable<Value> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> values(p0: Function<Value, T>?): MutableIterable<T> {
        TODO("Not yet implemented")
    }

    override fun asMap(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> asMap(p0: Function<Value, T>?): MutableMap<String, T> {
        TODO("Not yet implemented")
    }

    override fun id(): Long {
        TODO("Not yet implemented")
    }

    override fun labels(): MutableIterable<String> {
        TODO("Not yet implemented")
    }

    override fun hasLabel(p0: String?): Boolean {
        TODO("Not yet implemented")
    }
}