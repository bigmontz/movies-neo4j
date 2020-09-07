package com.neo4j.driver

import java.io.ByteArrayOutputStream

private val DEFAULT_VERSIONS: List<Pair<Int, Int>>
        = listOf(Pair(0, 0), Pair(0, 0), Pair(0, 0), Pair(0, 0))

fun identificationWithProtocolInformation(vararg versions : Pair<Int, Int>) : ByteArray {
    val buffer = ByteArrayOutputStream(20)
    buffer.write(0x60)
    buffer.write(0x60)
    buffer.write(0xb0)
    buffer.write(0x17)
    (versions.asList() + DEFAULT_VERSIONS).take(4).forEach {
        buffer.write(0x00)
        buffer.write(0x00)
        buffer.write(it.second)
        buffer.write(it.first)
    }
    return buffer.toByteArray()
}

