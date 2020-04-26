package com.github.whatsmock.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
data class User(
    @PrimaryKey val id: Int = 1,
    @ColumnInfo(name = "full_name") val fullName: String = "",
    @ColumnInfo(name = "image_path") val imagePath: String = ""
)
