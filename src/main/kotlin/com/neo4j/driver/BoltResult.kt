package com.neo4j.driver

import com.neo4j.driver.messenger.MessengerReader
import com.neo4j.driver.messenger.MessengerWriter
import org.neo4j.driver.Record
import org.neo4j.driver.Result
import org.neo4j.driver.summary.ResultSummary
import java.lang.RuntimeException
import java.util.function.Function
import java.util.stream.Stream
import kotlin.streams.toList

class BoltResult(
        private val reader: MessengerReader,
        private val writer: MessengerWriter,
        private val fields: List<String>) : Result {

    var next : Record? = null
    
    override fun list(): MutableList<Record> = stream().toList().toMutableList()

    override fun stream(): Stream<Record> {
        writer.write(MessageTags.PULL, mapOf("n" to -1))
        return Stream.generate {
            reader.read()
        }
                .takeWhile { (code, _) -> code == MessageTags.RECORD }
                .map { (_, record) -> BoltRecord(fields, record[0] as List<Any>) }
    }

    override fun keys(): MutableList<String> = fields.toMutableList()

    override fun hasNext(): Boolean {
        writer.write(MessageTags.PULL, mapOf("n" to 1))
        val (code, result) = reader.read()
        if(code == MessageTags.SUCCESS) {
            return false
        } else if (code == MessageTags.RECORD) {
            next = BoltRecord(fields = fields, record = result[0] as List<Any>)
            return true
        }
        throw RuntimeException("Error during get next record. Code=${code}, Fields=${result}")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun next(): Record = next!!

    override fun single(): Record {
        TODO("Not yet implemented")
    }

    override fun peek(): Record {
        TODO("Not yet implemented")
    }

    override fun <T : Any?> list(p0: Function<Record, T>?): MutableList<T> {
        TODO("Not yet implemented")
    }

    override fun consume(): ResultSummary {
        TODO("Not yet implemented")
    }
}