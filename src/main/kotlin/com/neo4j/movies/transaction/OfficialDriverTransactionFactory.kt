package com.neo4j.movies.transaction

import com.neo4j.movies.service.database.syncReadTransaction
import com.neo4j.movies.service.database.syncWriteTransaction
import org.neo4j.driver.Driver
import org.neo4j.driver.Transaction

class OfficialDriverTransactionFactory(private val driver: Driver) : TransactionFactory {
    override fun <T> read(consumer: (Transaction) -> T): T = driver.syncReadTransaction(consumer)
    override fun <T> write(consumer: (Transaction) -> T): T = driver.syncWriteTransaction(consumer)
}