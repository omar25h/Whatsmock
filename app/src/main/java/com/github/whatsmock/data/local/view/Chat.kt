package com.github.whatsmock.data.local.view

import androidx.room.DatabaseView
import java.util.*

@DatabaseView(
    viewName = "chat",
    value = """
    SELECT 
        user.id AS userId,
        user.full_name AS fullName,
        user.image_path AS imagePath,
        last_message.message AS lastMessage,
        last_message.created_at AS updatedAt
    FROM 
        user LEFT OUTER JOIN (
            SELECT user_id, message, max(created_at) AS created_at
            FROM message
            GROUP BY user_id
        ) AS last_message
        ON last_message.user_id = user.id
    """
)
data class Chat(
    val userId: Int,
    val fullName: String,
    val imagePath: String,
    val lastMessage: String?,
    val updatedAt: Date?
)
