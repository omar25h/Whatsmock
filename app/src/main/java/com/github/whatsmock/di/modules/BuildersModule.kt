package com.github.whatsmock.di.modules

import com.github.whatsmock.ui.chats.ChatsFragment
import com.github.whatsmock.ui.main.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BuildersModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity

    @ContributesAndroidInjector
    abstract fun contributeChatsFragmentInjector(): ChatsFragment
}