package com.neo4j.driver.messenger

import java.io.ByteArrayOutputStream

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

    fun write(tag: Int, vararg fields: Any) {
        val buffer = ByteArrayOutputStream()
        buffer.write(STRUCT_MARKER + fields.size)
        buffer.write(tag)
        fields.forEach { buffer.write(packer.pack(it)) }
        writeInChunks(buffer.toByteArray())
    }

    private fun writeInChunks(byteArray: ByteArray) {
        for (offset in byteArray.indices step CHUNK_SIZE) {
            val end = if (offset + CHUNK_SIZE <= byteArray.size) CHUNK_SIZE + 32767 else byteArray.size
            val chunk = byteArray.sliceArray(offset until end)
            writeChunk(chunk)
        }
    }

    private fun writeChunk(chunk: ByteArray) {
        write(packer.pack(chunk.size.toShort()))
        write(chunk)
        write(byteArrayOf(0x00, 0x00))
    }

    companion object {
        private const val CHUNK_SIZE: Int = 32767
        private val DEFAULT_VERSIONS: List<Pair<Int, Int>>
                = listOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))

    }
}