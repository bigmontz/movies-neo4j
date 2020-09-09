package com.neo4j.driver

object MessageTags {
    const val HELLO: Int = 0x01
    const val RUN: Int = 0x10
    const val PULL: Int = 0x3F
    const val SUCCESS: Int = 0x70
    const val RECORD: Int = 0x71
}