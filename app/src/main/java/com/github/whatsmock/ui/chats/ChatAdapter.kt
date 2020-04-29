package com.github.whatsmock.ui.chats

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Outline
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewOutlineProvider
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.github.whatsmock.R
import com.github.whatsmock.data.vo.Chat
import java.io.IOException
import java.io.InputStream
import java.text.SimpleDateFormat
import java.util.*

class ChatAdapter(
    private val context: Context,
    private val listener: ((chat: Chat) -> Unit)? = null
) :
    ListAdapter<Chat, ChatAdapter.ChatViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {

        holder.bind(currentList[position])
    }

    inner class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var textViewFullName: TextView = itemView.findViewById(R.id.textView_fullName)
        private var textViewMessage: TextView = itemView.findViewById(R.id.textView_message)
        private var textViewTime: TextView = itemView.findViewById(R.id.textView_time)
        private var imageViewAvatar: ImageView = itemView.findViewById(R.id.imageView_avatar)

        private val assetManager = context.assets

        private val dateFormatter = SimpleDateFormat("MMM dd\nhh:mm a", Locale.getDefault())

        fun bind(chat: Chat) {
            textViewFullName.text = chat.fullName
            textViewMessage.text = chat.lastMessage?.replace("\n", " ")
            textViewTime.textAlignment = TextView.TEXT_ALIGNMENT_CENTER
            textViewTime.setLineSpacing(0f, 1.5f)
            textViewTime.text = chat.updatedAt?.let { dateFormatter.format(it) }

            chat.imagePath?.let {
                imageViewAvatar.setImageBitmap(getBitmapFromAssets(it))
                imageViewAvatar.outlineProvider = object : ViewOutlineProvider() {
                    override fun getOutline(view: View?, outline: Outline?) {
                        outline!!.setRoundRect(0, 0, view!!.width, view.height, 25f)
                    }
                }
                imageViewAvatar.clipToOutline = true
            }

            itemView.setOnClickListener {
                listener?.invoke(chat)
            }
        }

        private fun getBitmapFromAssets(path: String): Bitmap {
            var stream: InputStream? = null
            try {
                stream = assetManager.open(path)
            } catch (e: IOException) {
                e.printStackTrace()
            }

            return BitmapFactory.decodeStream(stream)
        }
    }

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Chat>() {

            override fun areContentsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(oldItem: Chat, newItem: Chat): Boolean {
                return oldItem.userId == newItem.userId
            }
        }
    }
}