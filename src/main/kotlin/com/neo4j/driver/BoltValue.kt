package com.neo4j.driver

import com.neo4j.driver.messenger.Structure
import org.neo4j.driver.Value
import org.neo4j.driver.types.*
import java.lang.IllegalArgumentException
import java.time.*
import java.util.function.Function

class BoltValue (private val value: Any) : Value {

    override fun asString(): String {
        if(value !is String)
            throw IllegalArgumentException("Value $value is not a string")
        return value
    }

    override fun asNode(): Node {
        if(value !is Structure || value.type != Structure.Type.NODE)
            throw IllegalArgumentException("Value $value is not a node")
        return BoltNode(value.data)
    }


    override fun asInt(): Int = when(value) {
        is Byte -> value.toInt()
        is Short -> value.toInt()
        is Int -> value
        else -> throw IllegalArgumentException("Value $value is not an int")
    }

    override fun keys(): MutableIterable<String> {
        TODO("Not yet implemented")
    }

    override fun containsKey(p0: String?): Boolean {
        TODO("Not yet implemented")
    }

    override fun get(p0: Int): Value {
        TODO("Not yet implemented")
    }

    override fun get(p0: String?): Value {
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

    override fun size(): Int {
        TODO("Not yet implemented")
    }

    override fun values(): MutableIterable<Value> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> values(p0: Function<Value, T>?): MutableIterable<T> {
        TODO("Not yet implemented")
    }

    override fun asMap(p0: MutableMap<String, Any>?): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> asMap(p0: Function<Value, T>?, p1: MutableMap<String, T>?): MutableMap<String, T> {
        TODO("Not yet implemented")
    }

    override fun asMap(): MutableMap<String, Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> asMap(p0: Function<Value, T>?): MutableMap<String, T> {
        TODO("Not yet implemented")
    }

    override fun isEmpty(): Boolean {
        TODO("Not yet implemented")
    }

    override fun type(): Type {
        TODO("Not yet implemented")
    }

    override fun hasType(p0: Type?): Boolean {
        TODO("Not yet implemented")
    }

    override fun isTrue(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isFalse(): Boolean {
        TODO("Not yet implemented")
    }

    override fun isNull(): Boolean {
        TODO("Not yet implemented")
    }

    override fun asObject(): Any {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> computeOrDefault(p0: Function<Value, T>?, p1: T): T {
        TODO("Not yet implemented")
    }

    override fun asBoolean(): Boolean {
        TODO("Not yet implemented")
    }

    override fun asBoolean(p0: Boolean): Boolean {
        TODO("Not yet implemented")
    }

    override fun asByteArray(): ByteArray {
        TODO("Not yet implemented")
    }

    override fun asByteArray(p0: ByteArray?): ByteArray {
        TODO("Not yet implemented")
    }

    override fun asString(p0: String?): String {
        TODO("Not yet implemented")
    }

    override fun asNumber(): Number {
        TODO("Not yet implemented")
    }

    override fun asLong(): Long {
        TODO("Not yet implemented")
    }

    override fun asLong(p0: Long): Long {
        TODO("Not yet implemented")
    }


    override fun asInt(p0: Int): Int {
        TODO("Not yet implemented")
    }

    override fun asDouble(): Double {
        TODO("Not yet implemented")
    }

    override fun asDouble(p0: Double): Double {
        TODO("Not yet implemented")
    }

    override fun asFloat(): Float {
        TODO("Not yet implemented")
    }

    override fun asFloat(p0: Float): Float {
        TODO("Not yet implemented")
    }

    override fun asList(): MutableList<Any> {
        TODO("Not yet implemented")
    }

    override fun asList(p0: MutableList<Any>?): MutableList<Any> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> asList(p0: Function<Value, T>?): MutableList<T> {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> asList(p0: Function<Value, T>?, p1: MutableList<T>?): MutableList<T> {
        TODO("Not yet implemented")
    }

    override fun asEntity(): Entity {
        TODO("Not yet implemented")
    }

    override fun asRelationship(): Relationship {
        TODO("Not yet implemented")
    }

    override fun asPath(): Path {
        TODO("Not yet implemented")
    }

    override fun asLocalDate(): LocalDate {
        TODO("Not yet implemented")
    }

    override fun asLocalDate(p0: LocalDate?): LocalDate {
        TODO("Not yet implemented")
    }

    override fun asOffsetTime(): OffsetTime {
        TODO("Not yet implemented")
    }

    override fun asOffsetTime(p0: OffsetTime?): OffsetTime {
        TODO("Not yet implemented")
    }

    override fun asLocalTime(): LocalTime {
        TODO("Not yet implemented")
    }

    override fun asLocalTime(p0: LocalTime?): LocalTime {
        TODO("Not yet implemented")
    }

    override fun asLocalDateTime(): LocalDateTime {
        TODO("Not yet implemented")
    }

    override fun asLocalDateTime(p0: LocalDateTime?): LocalDateTime {
        TODO("Not yet implemented")
    }

    override fun asOffsetDateTime(): OffsetDateTime {
        TODO("Not yet implemented")
    }

    override fun asOffsetDateTime(p0: OffsetDateTime?): OffsetDateTime {
        TODO("Not yet implemented")
    }

    override fun asZonedDateTime(): ZonedDateTime {
        TODO("Not yet implemented")
    }

    override fun asZonedDateTime(p0: ZonedDateTime?): ZonedDateTime {
        TODO("Not yet implemented")
    }

    override fun asIsoDuration(): IsoDuration {
        TODO("Not yet implemented")
    }

    override fun asIsoDuration(p0: IsoDuration?): IsoDuration {
        TODO("Not yet implemented")
    }

    override fun asPoint(): Point {
        TODO("Not yet implemented")
    }

    override fun asPoint(p0: Point?): Point {
        TODO("Not yet implemented")
    }

}