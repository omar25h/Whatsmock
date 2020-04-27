package com.github.whatsmock.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import java.util.*

@Entity(
    tableName = "message",
    foreignKeys = [
        ForeignKey(entity = User::class, childColumns = ["user_id"], parentColumns = ["id"])
    ]
)
data class Message(
    @PrimaryKey(autoGenerate = true) val id: Int,
    @ColumnInfo(name = "user_id") val userId: Int,
    val message: String,
    @ColumnInfo(name = "is_forward") val isForward: Boolean,
    @ColumnInfo(name = "created_at") val createdAt: Date
)
