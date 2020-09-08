package com.neo4j.driver.messenger


const val FIELD_COUNT_MARKER: Int = 0xb0

open class Marker (val tiny: Int, val small: Int, val medium: Int, val large: Int) {
    val tinyRange : IntRange by lazy {
        SizeCategory.tyneRange.first + tiny .. SizeCategory.tyneRange.last + tiny
    }

}

object StringMarker: Marker(tiny = 0x80, small = 0xD0, medium = 0xD1, large = 0xD2)
object MapMarker: Marker(tiny = 0xA0, small = 0xD8, medium = 0xD9, large = 0xDA)
object ListMarker: Marker(tiny = 0x90, small = 0xD4, medium = 0xD5, large = 0xD6)

enum class SizeCategory {
    TINY, SMALL, MEDIUM, LARGE;

    companion object {
        fun getFromSize(size: Int): SizeCategory = when(size) {
            in tyneRange -> TINY
            in 0x10 until 0x100 -> SMALL
            in 0x100 until 0x10000 -> MEDIUM
            in 0x10000 until 0x100000000 -> LARGE
            else -> throw IllegalArgumentException("Package size too long")
        }

        val tyneRange: IntRange = 0 until 0x10

    }
}