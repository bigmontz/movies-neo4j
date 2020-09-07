package com.neo4j.driver

import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class Wire(socket: Socket) {
    private val inputStream: InputStream = socket.getInputStream();
    private val outputStream: OutputStream = socket.getOutputStream();

    fun read(length: Int): ByteArray {
        val buffer = ByteArray(length)
        inputStream.read(buffer, 0, length)
        return buffer
    }

    fun write(byteArray: ByteArray) {
        outputStream.write(byteArray)
    }

}