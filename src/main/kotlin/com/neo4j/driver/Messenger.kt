package com.neo4j.driver

import java.io.ByteArrayOutputStream
import java.lang.IllegalArgumentException
import java.lang.UnsupportedOperationException
import java.nio.ByteBuffer
import java.util.*
import java.util.stream.Stream

private val DEFAULT_VERSIONS: List<Pair<Int, Int>>
        = listOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))


class MessengerWriter(
        private val write: (ByteArray) -> Unit,
        private val packer: Packer = Packer()) {

    fun handshake(vararg versions : Pair<Int, Int>) {
        val buffer = ByteArrayOutputStream(20)
        buffer.write(0x60)
        buffer.write(0x60)
        buffer.write(0xb0)
        buffer.write(0x17)
        (versions.asList() + DEFAULT_VERSIONS).take(4).forEach {
            buffer.write(0x00)
            buffer.write(0x00)
            buffer.write(it.second)
            buffer.write(it.first)
        }
        write(buffer.toByteArray())
    }

    fun write(tag: Int, fields: Map<String, String>) {
        val buffer = ByteArrayOutputStream()
        buffer.write(0xB0 + fields.size)
        buffer.write(tag)
        buffer.write(packer.pack(fields))
        write(buffer.toByteArray())
    }
}

class MessengerReader(private val read: (Int) -> ByteArray, private val packer: Packer = Packer()) {
    fun version(): Pair<Int, Int> {
        val version = read(4)
        return Pair(version.last().toInt(), version.dropLast(1).last().toInt())
    }

    fun read() : ByteArray {
        return sequence<ByteArray> { readChunk() }
                .takeWhile { it.isNotEmpty() }
                .filter { it.isNotEmpty() }
                .reduce { acc, value -> acc + value}
    }

    private fun readChunk(): ByteArray {
        val size = packer.unpackU16(read(2))
        return if (size > 0) read(size.toInt()) else byteArrayOf()
    }

}

class Packer {

    fun pack(any: Any): ByteArray = when(any) {
        is String -> packString(any)
        is Map<*, *> -> packMap(any as Map<String, Any>)
        else -> throw UnsupportedOperationException()
    }

    fun unpackU16(byteArray: ByteArray): Short = ByteBuffer.wrap(byteArray).getShort(0)

    private fun packMap(map: Map<String, Any>): ByteArray {
        val header = header(map.size, StringMarker)
        val encoded = map.map { packString(it.key) + pack(it.value) }.reduce { acc, bytes -> acc + bytes }
        return header + encoded
    }

    private fun packString(str: String) : ByteArray {
        val encoded = Charsets.UTF_8.encode(str).array()
        println(encoded.joinToString(separator = "") { String.format("\\x%02X", it) } )
        val decoded = String(encoded, charset("UTF-8"))
        println(decoded)
        val header = header(encoded.size, MapMarker)
        return header + encoded
    }

    private fun header(size: Int, marker: Marker): ByteArray = when(SizeCategory.getFromSize(size)) {
        SizeCategory.TINY -> packU8(marker.tiny + size)
        SizeCategory.SMALL -> packU8(marker.small) + packU8(size)
        SizeCategory.MEDIUM -> packU8(marker.medium) + packU16(size)
        SizeCategory.LARGE -> packU8(marker.large) + packI32(size)
    }

    private fun packU8(int: Int): ByteArray = byteArrayOf(int.toUInt().toByte())

    private fun packU16(int: Int): ByteArray = ByteBuffer.allocate(Short.SIZE_BYTES).putShort(int.toShort()).array()

    private fun packI32(int: Int): ByteArray = ByteBuffer.allocate(Int.SIZE_BYTES).putInt(int).array()

    open class Marker (val tiny: Int, val small: Int, val medium: Int, val large: Int)

    object StringMarker: Marker(tiny = 0x80, small = 0xD0, medium = 0xD1, large = 0xD2)
    object MapMarker: Marker(tiny = 0xA0, small = 0xD8, medium = 0xD9, large = 0xDA)

    enum class SizeCategory {
        TINY, SMALL, MEDIUM, LARGE;

        companion object {
            fun getFromSize(size: Int): SizeCategory = when(size) {
                in 0..0x10 -> TINY
                in 0x10..0x100 -> SMALL
                in 0x100..0x10000 -> MEDIUM
                in 0x10000..0x100000000 -> LARGE
                else -> throw IllegalArgumentException("Package size too long")
            }
        }
    }

}
