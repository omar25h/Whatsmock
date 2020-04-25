package com.github.whatsmock.di.modules

import android.content.Context
import com.github.whatsmock.MainApplication
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule(private val app: MainApplication) {

    @Singleton
    @Provides
    fun provideAppContext(): Context {
        return app.applicationContext
    }
}