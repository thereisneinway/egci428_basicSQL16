package com.example.egci428_basicsql16

class Comment {
    var id: Long = 0
    var message: String = ""

    override fun toString(): String {
        return message.toString()
    }
}