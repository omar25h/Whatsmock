package com.github.whatsmock

import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatDelegate
import com.github.whatsmock.di.components.DaggerAppComponent
import com.github.whatsmock.di.modules.AppModule
import com.github.whatsmock.di.modules.RoomModule
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class MainApplication : Application(), HasAndroidInjector {

    @field:Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector(): AndroidInjector<Any> = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .application(this)
            .appModule(AppModule(this))
            .roomModule(RoomModule(this))
            .build()
            .inject(this)

        restoreThemeSetting()
    }

    // Check if the user has set theme preference.
    private fun restoreThemeSetting() {
        val theme = getSharedPreferences(
            packageName,
            Context.MODE_PRIVATE
        )!!.getInt(getString(R.string.pref_key_theme), -1)

        when (theme) {
            0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            2 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
        }
    }
}