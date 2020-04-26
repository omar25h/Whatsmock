package com.github.whatsmock.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.whatsmock.data.local.dao.ChatDao
import javax.inject.Inject
import javax.inject.Singleton
import com.github.whatsmock.data.vo.Chat as ChatVo

@Singleton
class ChatRepository @Inject constructor(
    private val chatDao: ChatDao
) {

    fun fetchChats(): LiveData<List<ChatVo>> {
        return chatDao.getChats()
            .map {
                it.map { chat ->
                    ChatVo(
                        chat.userId,
                        chat.imagePath,
                        chat.fullName,
                        chat.lastMessage,
                        chat.updatedAt
                    )
                }
            }
    }
}