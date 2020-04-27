package com.github.whatsmock.ui.main

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.github.whatsmock.R
import com.github.whatsmock.data.vo.Chat
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar = findViewById(R.id.toolbar)

        val navController = findNavController(R.id.fragment)

        toolbar.setupWithNavController(navController)

        navController.addOnDestinationChangedListener { _, destination, arguments ->
            when (destination.id) {
                R.id.chatsFragment -> {
                    toolbar.title = getString(R.string.app_name)
                }
                R.id.chatDetailFragment -> {
                    toolbar.title = (arguments?.get("chat") as? Chat)?.fullName ?: "Chat"
                }
            }
        }
    }
}
