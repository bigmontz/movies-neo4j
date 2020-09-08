package com.neo4j.driver.messenger

import java.lang.UnsupportedOperationException
import java.nio.ByteBuffer


class Packer {

    fun pack(any: Any): ByteArray = when(any) {
        is Short -> packU16(any)
        is String -> packString(any)
        is Map<*, *> -> packMap(any as Map<String, Any>)
        else -> throw UnsupportedOperationException()
    }

    private fun packMap(map: Map<String, Any>): ByteArray {
        val header = header(map.size, MapMarker)
        val encoded = map.map { packString(it.key) + pack(it.value) }.reduce { acc, bytes -> acc + bytes }
        return header + encoded
    }

    private fun packString(str: String) : ByteArray {
        val encoded = Charsets.UTF_8.encode(str).array().filter { it.toInt() != 0x00 }
        val header = header(encoded.size, StringMarker)
        return header + encoded
    }

    private fun header(size: Int, marker: Marker): ByteArray = when(SizeCategory.getFromSize(size)) {
        SizeCategory.TINY -> packU8(marker.tiny + size)
        SizeCategory.SMALL -> packU8(marker.small) + packU8(size)
        SizeCategory.MEDIUM -> packU8(marker.medium) + packU16(size)
        SizeCategory.LARGE -> packU8(marker.large) + packI32(size)
    }

    private fun packU8(int: Int): ByteArray = byteArrayOf(int.toUInt().toByte())

    private fun packU16(short: Short): ByteArray = ByteBuffer.allocate(Short.SIZE_BYTES).putShort(short).array()

    private fun packU16(int: Int): ByteArray = packU16(int.toShort())

    private fun packI32(int: Int): ByteArray = ByteBuffer.allocate(Int.SIZE_BYTES).putInt(int).array()

}

