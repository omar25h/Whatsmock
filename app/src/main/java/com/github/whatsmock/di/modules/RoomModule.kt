package com.github.whatsmock.di.modules

import androidx.room.Room
import com.github.whatsmock.MainApplication
import com.github.whatsmock.data.local.AppDatabase
import com.github.whatsmock.data.local.dao.ChatDao
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RoomModule(app: MainApplication) {

    private val database = Room
        .databaseBuilder(app, AppDatabase::class.java, "whatsmock-db")
        .fallbackToDestructiveMigration()
        .createFromAsset("whatsmock.db")
        .build()


    @Singleton
    @Provides
    fun provideDatabase(): AppDatabase {
        return database
    }

    @Singleton
    @Provides
    fun provideChatDao(): ChatDao {
        return database.chatDao()
    }
}
