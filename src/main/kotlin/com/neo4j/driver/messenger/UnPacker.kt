package com.neo4j.driver.messenger

import com.neo4j.driver.str
import com.neo4j.driver.unsignedInt
import java.lang.UnsupportedOperationException
import java.nio.ByteBuffer

class UnPacker {

    tailrec fun unpack(count: Int, byteArray: ByteArray, output: List<Any> = listOf()) : List<Any> = when(count) {
        0 -> output
        else -> {
            val (bytesConsumed, value) = unpack(byteArray)
            unpack(count - 1, byteArray.sliceArray(bytesConsumed until byteArray.size), output + value)
        }
    }

    private fun unpack(byteArray: ByteArray): Pair<Int, Any> = when(byteArray.first().unsignedInt()) {
        in MapMarker.tinyRange -> unpackTinyMap(byteArray)
        in StringMarker.tinyRange -> unpackTinyString(byteArray)
        else -> throw UnsupportedOperationException("Type ${byteArray.first().toInt()} (${byteArray.first().str()}) is not valid")
    }

    private fun unpackTinyMap(byteArray: ByteArray): Pair<Int, Any> {
        val length = byteArray.first().unsignedInt().minus(MapMarker.tiny)
        val map : MutableMap<String, Any> = mutableMapOf()
        var bytesRead = 1
        (1..length).forEach { _ ->
            val (keyBytes, key) = unpackTinyString(byteArray.sliceArray(bytesRead until byteArray.size))
            bytesRead+= keyBytes
            val (valueBytes, value) = unpack(byteArray.sliceArray(bytesRead until byteArray.size))
            bytesRead+= valueBytes
            map[key] = value
        }
        return bytesRead to map
    }

    private fun unpackTinyString(byteArray: ByteArray): Pair<Int, String> {
        val length = byteArray.first().unsignedInt().minus(StringMarker.tiny)
        val value = String(byteArray.sliceArray(1..length), charset = Charsets.UTF_8)
        return length + 1 to value
    }

    fun unpackU16(byteArray: ByteArray): Short = ByteBuffer.wrap(byteArray).getShort(0)


}