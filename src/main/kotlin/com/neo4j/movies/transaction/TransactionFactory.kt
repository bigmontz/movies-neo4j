package com.neo4j.movies.transaction

import org.neo4j.driver.Transaction

interface TransactionFactory {
    fun <T> read(consumer: (Transaction) -> T) : T
    fun <T> write(consumer: (Transaction) -> T): T
}