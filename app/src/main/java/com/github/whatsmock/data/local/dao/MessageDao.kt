package com.github.whatsmock.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.github.whatsmock.data.local.entity.Message

@Dao
interface MessageDao {

    /**
     * getMessages will retrieve the list of messages of a given chat by @param{userId} in
     * descending order.
     *
     * @param userId the ID of the user
     */
    @Query("SELECT * FROM message WHERE user_id = :userId ORDER BY created_at DESC")
    fun getMessages(userId: Int): LiveData<List<Message>>


    /**
     * insertMessages will persist list of messages submitted.
     *
     * @param messages Messages to persist
     */
    @Insert
    fun insertMessages(vararg messages: Message)
}