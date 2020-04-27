package com.github.whatsmock.ui.chats

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.github.whatsmock.R
import com.github.whatsmock.data.repository.ChatRepository
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ChatsFragment : Fragment() {

    @Inject
    lateinit var chatRepository: ChatRepository

    private lateinit var recyclerViewChats: RecyclerView

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_chats, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerViewChats = view.findViewById(R.id.recyclerView_chats)
        recyclerViewChats.layoutManager = LinearLayoutManager(context, VERTICAL, false)
        recyclerViewChats.adapter = ChatAdapter(requireContext()) {
            Log.i(this::class.simpleName, "Chat clicked: $it")
            findNavController().navigate(
                R.id.action_chatsFragment_to_chatDetailFragment,
                bundleOf(Pair("chat", it))
            )
        }

        chatRepository.fetchChats().observe(viewLifecycleOwner, Observer {
            (recyclerViewChats.adapter as? ChatAdapter)?.submitList(it)
        })
    }

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }
}