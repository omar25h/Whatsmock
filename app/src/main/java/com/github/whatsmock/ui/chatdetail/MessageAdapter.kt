package com.github.whatsmock.ui.chatdetail

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.whatsmock.R
import com.github.whatsmock.data.vo.Message

class MessageAdapter() : ListAdapter<Message, RecyclerView.ViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return when (viewType) {
            TYPE_MESSAGE_SENT -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_sent, parent, false)
                MessageSentViewHolder(view)
            }
            TYPE_MESSAGE_RECEIVED -> {
                val view = LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_message_received, parent, false)
                MessageReceivedViewHolder(view)
            }
            else -> {
                throw IllegalStateException("Cannot create view holder, view type is unknown $viewType")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_MESSAGE_SENT -> (holder as MessageSentViewHolder).bind(currentList[position])
            TYPE_MESSAGE_RECEIVED -> (holder as MessageReceivedViewHolder).bind(currentList[position])
        }
    }

    override fun getItemViewType(position: Int): Int {
        if (currentList[position].isForward) {
            return TYPE_MESSAGE_SENT
        }

        return TYPE_MESSAGE_RECEIVED
    }

    inner class MessageSentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewMessage: TextView = itemView.findViewById(R.id.textView_message)

        fun bind(message: Message) {

            textViewMessage.text = message.message
        }
    }

    inner class MessageReceivedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val textViewMessage: TextView = itemView.findViewById(R.id.textView_message)

        fun bind(message: Message) {

            textViewMessage.text = message.message
        }
    }

    companion object {
        const val TYPE_MESSAGE_SENT = 0
        const val TYPE_MESSAGE_RECEIVED = 1

        val diffCallback = object : DiffUtil.ItemCallback<Message>() {
            override fun areContentsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Message, newItem: Message): Boolean {
                return oldItem.id == newItem.id
            }
        }
    }
}