package com.neo4j.driver

import com.neo4j.driver.messenger.MessengerReader
import com.neo4j.driver.messenger.MessengerWriter
import org.neo4j.driver.*

class BoltTransaction(
        private val writer: MessengerWriter,
        private val reader: MessengerReader
) : Transaction {

    override fun run(query: String?, params: MutableMap<String, Any>?): Result {
        writer.write(MessageTags.RUN, query!!, params!!)
        val (code, fields) = reader.read()
        println("$code => $fields")
        TODO("Not yet implemented :D")
    }

    override fun close() {
        TODO("Not yet implemented")
    }

    override fun isOpen(): Boolean {
        TODO("Not yet implemented")
    }

    override fun run(p0: String?, p1: Value?): Result {
        TODO("Not yet implemented")
    }

    override fun run(p0: String?, p1: Record?): Result {
        TODO("Not yet implemented")
    }

    override fun run(p0: String?): Result {
        TODO("Not yet implemented")
    }

    override fun run(p0: Query?): Result {
        TODO("Not yet implemented")
    }

    override fun commit() {
        TODO("Not yet implemented")
    }

    override fun rollback() {
        TODO("Not yet implemented")
    }


}