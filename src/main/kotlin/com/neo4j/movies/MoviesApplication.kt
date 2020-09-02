package com.neo4j.movies

import org.neo4j.driver.AuthTokens
import org.neo4j.driver.Driver
import org.neo4j.driver.GraphDatabase
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean

@SpringBootApplication
class MoviesApplication
	@Autowired constructor(
			@Value("\${neo4j.uri}") uri: String,
			@Value("\${neo4j.username}") user: String,
			@Value("\${neo4j.password}") password: String) : AutoCloseable {

	private lateinit var driver: Driver

	init {
		LOG.info("Creating driver for uri=${uri} and user=${user}")
		driver = GraphDatabase.driver(uri, AuthTokens.basic(user, password))
	}

	@Bean
	fun neo4jDriver() = driver

	override fun close() {
		driver.close();
	}

	companion object {
		val LOG: Logger = LoggerFactory.getLogger(MoviesApplication::class.java)
	}

}

fun main(args: Array<String>) {
	runApplication<MoviesApplication>(*args)
}
