package com.github.whatsmock.data.vo

import java.util.*

data class Chat(
    val userId: Int,
    val imagePath: String?,
    val fullName: String,
    val lastMessage: String?,
    val updatedAt: Date?
)