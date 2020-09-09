package com.neo4j.driver

import com.neo4j.driver.messenger.MessengerReader
import com.neo4j.driver.messenger.MessengerWriter
import org.neo4j.driver.*
import java.lang.RuntimeException

class BoltTransaction(
        private val writer: MessengerWriter,
        private val reader: MessengerReader
) : Transaction {

    override fun run(query: String?, params: MutableMap<String, Any>?): Result {
        writer.write(MessageTags.RUN, query!!, params!!, mapOf<String, Any>())
        val (code, fields) = reader.read()
        if(code != MessageTags.SUCCESS)
            throw RuntimeException("Error running the query: code=$code, $fields=$fields")
        if(fields.isEmpty())
            throw RuntimeException("Unexpected error, result fields are empty")
        if(fields.first() !is Map<*, *>)
            throw RuntimeException("Unexpected error Result field one is not a map")
        val first = fields.first() as Map<String, Any>
        val resultFields = first["fields"] as List<String>
        return BoltResult(reader, writer, resultFields)
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