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

    private var hasNext: Boolean = true

    override fun list(): MutableList<Record> = stream().toList().toMutableList()

    override fun stream(): Stream<Record> =
            Stream.generate {
                writer.write(MessageTags.PULL, mapOf("n" to -1))
                reader.read()
            }
                    .takeWhile { (code, _) -> code == MessageTags.RECORD }
                    .map { (_, record) -> BoltRecord(fields, record[0] as List<Any>) }

    override fun keys(): MutableList<String> = fields.toMutableList()

    override fun remove() {
        TODO("Not yet implemented")
    }

    override fun hasNext(): Boolean = hasNext

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