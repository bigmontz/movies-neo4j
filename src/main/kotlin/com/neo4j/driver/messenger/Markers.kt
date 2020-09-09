package com.neo4j.driver.messenger


const val STRUCT_MARKER: Int = 0xb0
val STRUCT_MARKER_RANGE: IntRange =
        SizeCategory.tyneRange.first + STRUCT_MARKER .. SizeCategory.tyneRange.last + STRUCT_MARKER

const val NODE_MARKER: Int = 0x4E

open class Marker (val tiny: Int, val small: Int, val medium: Int, val large: Int) {
    open val tinyRange : IntRange by lazy {
        SizeCategory.tyneRange.first + tiny .. SizeCategory.tyneRange.last + tiny
    }
}

object StringMarker: Marker(tiny = 0x80, small = 0xD0, medium = 0xD1, large = 0xD2)
object MapMarker: Marker(tiny = 0xA0, small = 0xD8, medium = 0xD9, large = 0xDA)
object ListMarker: Marker(tiny = 0x90, small = 0xD4, medium = 0xD5, large = 0xD6)
object IntMarker: Marker(tiny = 0x00, small = 0xC8, medium = 0xC9, large = 0xCA) {
    val xlarge: Int = 0xCB
    override val tinyRange: IntRange by lazy {
        -0x10 until 0x80
    }
}

enum class SizeCategory {
    TINY, SMALL, MEDIUM, LARGE, XLARGE;

    companion object {
        fun getFromSize(size: Int): SizeCategory = when(size) {
            in tyneRange -> TINY
            in 0x10 until 0x100 -> SMALL
            in 0x100 until 0x10000 -> MEDIUM
            in 0x10000 until 0x100000000 -> LARGE
            else -> throw IllegalArgumentException("Package size too long")
        }

        fun getFromInteger(value: Long): SizeCategory = when(value) {
            in IntMarker.tinyRange -> TINY
            in Byte.MIN_VALUE.toLong() .. Byte.MAX_VALUE.toLong() -> SMALL
            in Short.MIN_VALUE.toLong() .. Short.MAX_VALUE.toLong() -> MEDIUM
            in Int.MIN_VALUE.toLong() .. Int.MAX_VALUE.toLong() -> LARGE
            in Long.MIN_VALUE .. Long.MAX_VALUE -> XLARGE
            else -> throw java.lang.IllegalArgumentException("Value too big")
        }

        val tyneRange: IntRange = 0 until 0x10

    }
}