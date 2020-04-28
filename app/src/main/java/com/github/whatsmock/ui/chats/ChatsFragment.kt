package com.github.whatsmock.ui.chats

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.os.Build
import android.os.Build.VERSION_CODES.Q
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.appcompat.app.AppCompatDelegate.*
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager.VERTICAL
import androidx.recyclerview.widget.RecyclerView
import com.github.whatsmock.R
import com.github.whatsmock.data.repository.ChatRepository
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class ChatsFragment : Fragment() {

    @Inject
    lateinit var chatRepository: ChatRepository

    private lateinit var sharedPreferences: SharedPreferences

    private lateinit var recyclerViewChats: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)

        sharedPreferences =
            requireContext().getSharedPreferences(requireContext().packageName, MODE_PRIVATE)!!
    }

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

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.chats, menu)
        if (Build.VERSION.SDK_INT < Q) {
            menu.removeItem(R.id.menu_item_more)
        }
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_item_dark_theme -> {
                showSelectThemeDialog()
                return true
            }
        }

        return super.onOptionsItemSelected(item)
    }

    // Show theme chooser only on devices having Android 10 or higher.
    private fun showSelectThemeDialog() {
        val prefKeyTheme = getString(R.string.pref_key_theme)

        MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.action_select_theme))
            .setSingleChoiceItems(
                R.array.themes,
                sharedPreferences.getInt(prefKeyTheme, 2)
            ) { dialog, which ->
                when (which) {
                    0 -> setDefaultNightMode(MODE_NIGHT_NO)
                    1 -> setDefaultNightMode(MODE_NIGHT_YES)
                    2 -> setDefaultNightMode(MODE_NIGHT_FOLLOW_SYSTEM)
                }
                sharedPreferences.edit().putInt(prefKeyTheme, which).apply()
                dialog.dismiss()
            }
            .create()
            .show()
    }
}