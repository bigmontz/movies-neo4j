package com.neo4j.driver

import com.neo4j.driver.messenger.MessengerReader
import com.neo4j.driver.messenger.MessengerWriter
import org.neo4j.driver.Transaction
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import java.lang.RuntimeException
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

    fun <T> withAutoCommitTransaction(consumer : (Transaction) -> T) : T {
        val transaction = BoltTransaction(writer, reader)
        return consumer(transaction)
    }

    fun <T> withWriteTransaction(consumer: (Transaction) -> T) : T {
        return BoltTransaction(writer, reader, false).use {
            val result = consumer(it)
            it.commit()
            result
        }
    }

    private fun handshake(): Pair<Int, Int> {
        writer.handshake(Pair(4, 1))
        return reader.version()
    }

    private fun hello(user: String, password: String) {
        val credentials = mapOf(
                "user_agent" to "movies\\1.0",
                "scheme" to "basic",
                "principal" to user,
                "credentials" to password)

        writer.write(MessageTags.HELLO, credentials)

        val (tag, fields) = reader.read()

        if(tag != MessageTags.SUCCESS) {
            throw RuntimeException("Error during the login, connection DEFUNCT")
        } else if (fields.isEmpty()) {
            throw RuntimeException("Unauthorized user")
        }
        
        LOG.info("$tag, ${fields.first()}")
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

    companion object {
        val LOG : Logger = LoggerFactory.getLogger(Bolt::class.java)
    }

}

data class Credentials(val user: String, val password: String)