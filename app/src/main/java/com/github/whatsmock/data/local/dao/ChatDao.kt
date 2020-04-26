package com.github.whatsmock.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.whatsmock.data.local.view.Chat

@Dao
interface ChatDao {
    @Query("SELECT * FROM chat")
    fun getChats(): LiveData<List<Chat>>
}