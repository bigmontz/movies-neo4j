package com.neo4j.driver

import java.io.IOException
import java.io.InputStream
import java.io.OutputStream
import java.net.Socket

class Wire(socket: Socket) {
    private val inputStream: InputStream = socket.getInputStream();
    private val outputStream: OutputStream = socket.getOutputStream();

    fun read(length: Int): ByteArray {
        val buffer = ByteArray(length)
        var bytesRead = 0
        do {
            val result = inputStream.read(buffer, bytesRead, length)
            if(result == -1) {
                throw IOException("End of stream")
            }
            bytesRead += result
        } while (bytesRead < length)
        return buffer
    }

    fun write(byteArray: ByteArray) {
        outputStream.write(byteArray)
    }

}