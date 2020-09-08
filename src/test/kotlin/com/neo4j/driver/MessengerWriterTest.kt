package com.neo4j.driver

import com.neo4j.driver.messenger.MessengerWriter
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

internal class MessengerWriterTest {

    @Test
    fun write_should_support_hello_message() {
        val expectedHex = "b1 01 a4 8b 63 72 65 64 65 6e 74 69 61 6c 73 84 74 65 73 74 86 73 63 68 65 6d 65 85 62 61 73 69 63 8a 75 73 65 72 5f 61 67 65 6e 74 85 64 63 74 2f 32 89 70 72 69 6e 63 69 70 61 6c 85 6e 65 6f 34 6a";
        val credentials = mapOf(
                "user_agent" to "dct/2",
                "scheme" to "basic",
                "principal" to "bolt",
                "password" to "test")
        MessengerWriter({
            val hex = it.joinToString(separator = " ") { i -> String.format("%02x", i) }
            assertEquals(expectedHex, hex)
        }).write(0x01, credentials)
    }
}