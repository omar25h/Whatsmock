package com.github.whatsmock.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.github.whatsmock.data.local.dao.ChatDao
import com.github.whatsmock.data.local.entity.Message
import com.github.whatsmock.data.local.entity.User
import com.github.whatsmock.data.local.view.Chat

@Database(
    entities = [
        User::class,
        Message::class
    ],
    views = [
        Chat::class
    ],
    version = AppDatabase.VERSION,
    exportSchema = true
)
@TypeConverters(TimestampConverter::class)
abstract class AppDatabase : RoomDatabase() {

    abstract fun chatDao(): ChatDao

    companion object {
        const val VERSION = 1
    }
}