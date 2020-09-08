package com.neo4j.movies.transaction

import com.neo4j.driver.Bolt
import com.neo4j.driver.Credentials
import org.neo4j.driver.Transaction

class ExperimentalDriverTransactionFactory(
        private val credentials: Credentials,
        private val port: Int,
        private val host: String
) : TransactionFactory {

    override fun <T> read(consumer: (Transaction) -> T): T = createBolt().withAutoCommitTransaction(consumer)
    override fun <T> write(consumer: (Transaction) -> T): T = createBolt().withAutoCommitTransaction(consumer)

    private fun createBolt(): Bolt {
        return Bolt(host, port, credentials)
    }
}