package com.neo4j.driver


fun ByteArray.str(): String = this.joinToString(separator = "") { it.str() }
fun Byte.str(): String = "\\x${ String.format("%02X", this) }"
fun Byte.unsignedInt() : Int = this.toUByte().toInt()
