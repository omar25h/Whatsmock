package com.github.whatsmock.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import com.github.whatsmock.data.local.dao.ChatDao
import com.github.whatsmock.data.local.dao.MessageDao
import com.github.whatsmock.data.local.entity.Message
import javax.inject.Inject
import javax.inject.Singleton
import com.github.whatsmock.data.vo.Chat as ChatVo
import com.github.whatsmock.data.vo.Message as MessageVo

@Singleton
class ChatRepository @Inject constructor(
    private val chatDao: ChatDao,
    private val messageDao: MessageDao
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

    /**
     * fetchMessages will load messages associated with #link{userId}.
     *
     * @param userId The ID of the user
     */
    fun fetchMessages(userId: Int): LiveData<List<MessageVo>> {
        return messageDao.getMessages(userId).map {
            it.map { message ->
                MessageVo(
                    message.id,
                    message.userId,
                    message.isForward,
                    message.message,
                    message.createdAt
                )
            }
        }
    }

    /**
     * sendMessage will persist @param(message) to local database.
     *
     * @param message the message value object.
     */
    fun sendMessage(message: MessageVo) {
        messageDao.insertMessages(
            Message(
                0,
                message.userId,
                message.message,
                message.isForward,
                message.createdAt
            )
        )
    }
}