package com.neo4j.driver.messenger

import com.neo4j.driver.str
import com.neo4j.driver.unsignedInt
import java.lang.IllegalArgumentException
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
        in IntMarker.tinyRange -> unpackTinyByte(byteArray)
        in ListMarker.tinyRange -> unpackTinyList(byteArray)
        in STRUCT_MARKER_RANGE -> unpackStructure(byteArray)
        StringMarker.small -> unpackSmallString(byteArray)
        IntMarker.medium -> unpackShortBe(byteArray)
        else -> throw UnsupportedOperationException("Type ${byteArray.first().toInt()} (${byteArray.first().str()}) is not valid")
    }

    private fun unpackStructure(byteArray: ByteArray): Pair<Int, Structure> {
        val length = byteArray.first().unsignedInt().minus(STRUCT_MARKER)
        val marker = byteArray[1]
        var bytesRead = 2
        return when(marker.unsignedInt()) {
            NODE_MARKER -> {
                val list : MutableList<Any> = mutableListOf()
                (1..length).forEach { _ ->
                    val (valueBytes, value) = unpack(byteArray.sliceArray(bytesRead until byteArray.size))
                    bytesRead += valueBytes
                    list += value
                }
                bytesRead to Structure(Structure.Type.NODE, list)
            }
            else -> throw UnsupportedOperationException("Marker ${marker.toInt()}(${marker.str()} not supported yet")
        }
    }

    private fun unpackTinyList(byteArray: ByteArray): Pair<Int, List<Any>> {
        val length = byteArray.first().unsignedInt().minus(ListMarker.tiny)
        val list : MutableList<Any> = mutableListOf()
        var bytesRead = 1
        (1..length).forEach { _ ->
            val (valueBytes, value) = unpack(byteArray.sliceArray(bytesRead until byteArray.size))
            bytesRead += valueBytes
            list += value
        }
        return bytesRead to list
    }

    private fun unpackTinyByte(byteArray: ByteArray): Pair<Int, Byte> {
        return Byte.SIZE_BYTES to byteArray.first()
    }

    private fun unpackShortBe(byteArray: ByteArray): Pair<Int, Short> {
        val value = ByteBuffer.wrap(byteArray.sliceArray(1..Short.SIZE_BYTES + 1)).short
        return Short.SIZE_BYTES + 1 to value
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

    private fun unpackSmallString(byteArray: ByteArray): Pair<Int, Any> {
        val length = byteArray[1].unsignedInt()
        val value = String(byteArray.sliceArray(2..length+1), charset = Charsets.UTF_8)
        return length + 2 to value
    }

    fun unpackU16(byteArray: ByteArray): Short = ByteBuffer.wrap(byteArray).getShort(0)


}