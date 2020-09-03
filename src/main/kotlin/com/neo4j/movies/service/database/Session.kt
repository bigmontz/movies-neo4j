package com.neo4j.movies.service.database;

import org.neo4j.driver.Driver
import org.neo4j.driver.Session
import org.neo4j.driver.Transaction

fun <T> Driver.sync(command: (Session) -> T) : T {
    return session().use(command)
}

fun <T> Driver.syncWriteTransaction(command: (Transaction) -> T): T = this.sync { it.writeTransaction(command) }
fun <T> Driver.syncReadTransaction(command: (Transaction) -> T): T = this.sync { it.readTransaction(command) }