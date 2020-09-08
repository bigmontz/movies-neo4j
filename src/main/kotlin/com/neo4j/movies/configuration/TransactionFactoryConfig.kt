package com.neo4j.movies.configuration

import com.neo4j.driver.Credentials
import com.neo4j.movies.transaction.ExperimentalDriverTransactionFactory
import com.neo4j.movies.transaction.OfficialDriverTransactionFactory
import com.neo4j.movies.transaction.TransactionFactory
import org.neo4j.driver.Driver
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.net.URI

@Configuration
class TransactionFactoryConfig {

    @Bean
    fun transactionFactory(
            @Autowired driver: Driver,
            @Value("\${org.neo4j.driver.uri}") uri: URI,
            @Value("\${org.neo4j.driver.authentication.username}") username: String,
            @Value("\${org.neo4j.driver.authentication.password}") password: String,
            @Value("\${transaction.experimental}") experimental: Boolean) : TransactionFactory {
        return if (experimental) {
            LOG.info("Creating experimental transaction factory")
            ExperimentalDriverTransactionFactory(Credentials(username, password), uri.port, uri.host)
        } else {
            LOG.info("Creating official transaction factory")
            OfficialDriverTransactionFactory(driver)
        }
    }

    companion object {
        private val LOG = LoggerFactory.getLogger(TransactionFactoryConfig::class.java)
    }


}