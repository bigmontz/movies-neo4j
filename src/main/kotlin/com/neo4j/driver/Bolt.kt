package com.neo4j.driver

import org.apache.tomcat.util.buf.HexUtils
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.net.Socket

class Bolt(host: String, port: Int): AutoCloseable {
    private val socket: Socket = Socket(host, port)
    private val wire: Wire = Wire(socket)

    init {
        LOG.info("Connection accepted")
        val version = handshake()
        LOG.info("Connected with version $version")
    }

    private fun handshake(): Pair<Int, Int> {
        LOG.info("Handing shaking")
        val message = identificationWithProtocolInformation(Pair(4, 0))
        LOG.info("Sending ${message.str()}")
        wire.write(message)
        val response = wire.read(4)
        LOG.info("Read ${response.str()}")
        return Pair(response.last().toInt(), response.dropLast(1).last().toInt())
    }

    override fun close() {
        socket.close()
        LOG.info("Connection closed")
    }

    private fun ByteArray.str(): String = HexUtils.toHexString(this)


    companion object {
        val LOG : Logger = LoggerFactory.getLogger(Bolt::class.java)
    }

}