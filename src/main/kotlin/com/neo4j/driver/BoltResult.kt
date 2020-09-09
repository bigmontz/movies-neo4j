package com.neo4j.driver

import com.neo4j.driver.messenger.MessengerReader
import com.neo4j.driver.messenger.MessengerWriter
import org.neo4j.driver.Record
import org.neo4j.driver.Result
import org.neo4j.driver.summary.ResultSummary
import java.util.function.Function
import java.util.stream.Stream
import kotlin.streams.toList

class BoltResult(
        private val reader: MessengerReader,
        private val writer: MessengerWriter,
        private val fields: List<String>) : Result {


    override fun list(): MutableList<Record> = stream().toList().toMutableList()

    override fun stream(): Stream<Record> {
        println("pull new data")
        writer.write(MessageTags.PULL, mapOf("n" to -1))
        return Stream.generate {
            reader.read()
        }
                .takeWhile { (code, _) -> code == MessageTags.RECORD }
                .map { (_, record) -> BoltRecord(fields, record[0] as List<Any>) }
    }

    override fun keys(): MutableList<String> = fields.toMutableList()


    override fun hasNext(): Boolean {
        println("pull new data")
        writer.write(MessageTags.PULL, mapOf("n" to 1))
        val (code, fields) = reader.read()
        if(code == MessageTags.SUCCESS) {
            val field = fields[0] as Map<String, Any>
            return (field["t_last"] as Byte).toInt() == 0
        }
        println("$code and $fields")
        TODO("Not yet implemented")
    }

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun next(): Record {
        TODO("Not yet implemented")
    }

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