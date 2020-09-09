package com.neo4j.driver.messenger

import com.neo4j.driver.unsignedInt
import java.io.ByteArrayOutputStream


class MessengerReader(private val read: (Int) -> ByteArray, private val unPacker: UnPacker = UnPacker()) {
    fun version(): Pair<Int, Int> {
        val version = read(4)
        return Pair(version.last().toInt(), version.dropLast(1).last().toInt())
    }

    fun read() : Pair<Int, List<Any>> {
        val outputStream = ByteArrayOutputStream()
        var chunk = readChunk()
        while (chunk != null) {
            outputStream.write(chunk)
            chunk = readChunk()
        }
        val response = outputStream.toByteArray()
        val (fieldsMarker, tag) = response
        val fieldsCount = fieldsMarker.unsignedInt() - STRUCT_MARKER
        return tag.toInt() to unPacker.unpack(count = fieldsCount, byteArray = response.sliceArray(2 until response.size))
    }

    private fun readChunk(): ByteArray? {
        var sizeByteArray = read(2)
        val size = unPacker.unpackU16(sizeByteArray)
        return if (size > 0) read(size.toInt()) else null
    }
}