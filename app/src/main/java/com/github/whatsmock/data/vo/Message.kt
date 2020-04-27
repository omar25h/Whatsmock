package com.github.whatsmock.data.vo

import java.util.*

data class Message(
    val id: Int?,
    val userId: Int,
    val isForward: Boolean,
    val message: String,
    val createdAt: Date
)