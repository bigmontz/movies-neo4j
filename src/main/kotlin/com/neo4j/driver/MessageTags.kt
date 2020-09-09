package com.neo4j.driver

object MessageTags {
    const val HELLO: Int = 0x01
    const val RUN: Int = 0x10
    const val BEGIN: Int = 0x11
    const val COMMIT: Int = 0x12
    const val ROLLBACK: Int = 0x13
    const val PULL: Int = 0x3F
    const val DISCARD: Int = 0x2F
    const val SUCCESS: Int = 0x70
    const val RECORD: Int = 0x71
}