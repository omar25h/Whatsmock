package com.github.whatsmock.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Query
import com.github.whatsmock.data.local.view.Chat

@Dao
interface ChatDao {
    /**
     * getChats will retrieve list of chats using the #link{chat} database view.
     * Results will be ordered by last message sent in descending order, and then by fullName value
     */
    @Query("SELECT * FROM chat ORDER BY updatedAt DESC, fullName ASC")
    fun getChats(): LiveData<List<Chat>>
}