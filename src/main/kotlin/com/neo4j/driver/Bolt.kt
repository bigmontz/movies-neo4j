package com.neo4j.driver

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.Socket

class Bolt(host: String, port: Int, credentials: Credentials): AutoCloseable {
    private val socket: Socket = Socket(host, port)
    private val wire: Wire = Wire(socket)
    private val writer: MessengerWriter = MessengerWriter(this::sendOverTheWire)
    private val reader: MessengerReader = MessengerReader(this::readOverTheWire)

    init {
        LOG.info("Connection accepted")
        val version = handshake()
        LOG.info("Connected with version $version")
        hello(credentials.user, credentials.password)
    }

    private fun handshake(): Pair<Int, Int> {
        writer.handshake(Pair(4, 1))
        return reader.version()
    }

    private fun hello(user: String, password: String) {
        val credentials = mapOf(
                "user_agent" to "movies/1",
                "scheme" to "basic",
                "principal" to user,
                "credentials" to password)

        writer.write(HELLO, credentials)

        val response = reader.read()
    }

    private fun sendOverTheWire(byteArray: ByteArray) {
        LOG.info("Sending ${byteArray.str()}")
        wire.write(byteArray)
    }

    private fun readOverTheWire(length: Int): ByteArray {
        val chunk = wire.read(length)
        LOG.info("Read ${chunk.str()}")
        return chunk
    }

    override fun close() {
        socket.close()
        LOG.info("Connection closed")
    }

    private fun ByteArray.str(): String = this.joinToString(separator = "") { "\\x${ String.format("%02X", it) }" }

    companion object {
        val LOG : Logger = LoggerFactory.getLogger(Bolt::class.java)
        private const val HELLO: Int = 0x01
    }

}

data class Credentials(val user: String, val password: String)