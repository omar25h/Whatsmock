package com.github.whatsmock.ui.chatdetail

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.github.whatsmock.R
import com.github.whatsmock.data.repository.ChatRepository
import com.github.whatsmock.data.vo.Chat
import com.github.whatsmock.data.vo.Message
import com.google.android.material.textfield.TextInputEditText
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

class ChatDetailFragment : Fragment() {

    @field:Inject
    lateinit var chatRepository: ChatRepository

    private val args: ChatDetailFragmentArgs by navArgs()

    private lateinit var chat: Chat

    private lateinit var recyclerViewMessages: RecyclerView
    private lateinit var textInputEditTextMessage: TextInputEditText
    private lateinit var imageButtonSend: ImageButton

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chatdetail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chat = args.chat as Chat

        recyclerViewMessages = view.findViewById(R.id.recyclerView_messages)
        textInputEditTextMessage = view.findViewById(R.id.textInputEditText_message)
        imageButtonSend = view.findViewById(R.id.button_send)

        setupRecyclerView()
        setupButton()

        chatRepository.fetchMessages(chat.userId).observe(viewLifecycleOwner, Observer {
            (recyclerViewMessages.adapter as MessageAdapter).submitList(it)
        })

    }

    private fun setupRecyclerView() {
        val layoutManager = LinearLayoutManager(requireContext(), VERTICAL, true)
        layoutManager.stackFromEnd = true

        recyclerViewMessages.layoutManager = layoutManager

        val messageAdapter = MessageAdapter()
        messageAdapter.registerAdapterDataObserver(object :
            RecyclerView.AdapterDataObserver() {
            override fun onItemRangeInserted(positionStart: Int, itemCount: Int) {
                super.onItemRangeInserted(positionStart, itemCount)

                // Scroll to latest message.
                recyclerViewMessages.scrollToPosition(0)
            }
        })

        recyclerViewMessages.adapter = messageAdapter
    }

    private fun setupButton() {
        imageButtonSend.isEnabled = false

        textInputEditTextMessage.addTextChangedListener {
            imageButtonSend.isEnabled = !(it.isNullOrEmpty() || it.trim().isEmpty())
        }

        imageButtonSend.setOnClickListener {
            val text = textInputEditTextMessage.text.toString().trim()

            val message = Message(
                null,
                chat.userId,
                true,
                text,
                Calendar.getInstance(Locale.getDefault()).time
            )

            GlobalScope.launch {
                chatRepository.sendMessage(message)

                lifecycleScope.launch {
                    textInputEditTextMessage.text = null
                }

                delay(500)

                val receivedMessage = Message(
                    null,
                    chat.userId,
                    false,
                    text,
                    Calendar.getInstance(Locale.getDefault()).time
                )
                chatRepository.sendMessage(receivedMessage)
                chatRepository.sendMessage(receivedMessage)
            }
        }
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}