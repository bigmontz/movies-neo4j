package com.neo4j.driver

import org.neo4j.driver.Record
import org.neo4j.driver.Value
import org.neo4j.driver.types.Entity
import org.neo4j.driver.types.Node
import org.neo4j.driver.types.Path
import org.neo4j.driver.types.Relationship
import org.neo4j.driver.util.Pair
import java.lang.IllegalArgumentException
import java.util.function.Function

class BoltRecord(private val fields: List<String>, private val record: List<Any>) : Record {

    override fun get(fieldName: String?): Value {
        val fieldIndex = fields.indexOf(fieldName!!)
        if(fieldIndex < 0 ) {
            throw IllegalArgumentException("Field $fieldName doesn't exist")
        }
        return BoltValue(record[fieldIndex])
    }

    override fun get(p0: Int): Value {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Value?): Value {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Any?): Any {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Number?): Number {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Entity?): Entity {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Node?): Node {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Path?): Path {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Relationship?): Relationship {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: MutableList<Any>?): MutableList<Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> get(p0: String?, p1: MutableList<T>?, p2: Function<Value, T>?): MutableList<T> {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: MutableMap<String, Any>?): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> get(p0: String?, p1: MutableMap<String, T>?, p2: Function<Value, T>?): MutableMap<String, T> {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Int): Int {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Long): Long {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: String?): String {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Float): Float {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?, p1: Double): Double {
        TODO("Not yet implemented")
    }

    override fun keys(): MutableList<String> {
        TODO("Not yet implemented")
    }

    override fun values(): MutableList<Value> {
        TODO("Not yet implemented")
    }

    override fun containsKey(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun index(p0: String?): Int {
        TODO("Not yet implemented")
    }

    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun asMap(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> asMap(p0: Function<Value, T>?): MutableMap<String, T> {
        TODO("Not yet implemented")
    }

    override fun fields(): MutableList<Pair<String, Value>> {
        TODO("Not yet implemented")
    }

}