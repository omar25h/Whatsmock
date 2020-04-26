package com.github.whatsmock.di.components

import android.app.Application
import com.github.whatsmock.MainApplication
import com.github.whatsmock.di.modules.AppModule
import com.github.whatsmock.di.modules.BuildersModule
import com.github.whatsmock.di.modules.RoomModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        BuildersModule::class,
        RoomModule::class
    ]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder

        fun appModule(appModule: AppModule): Builder

        fun roomModule(roomModule: RoomModule): Builder

        fun build(): AppComponent
    }

    fun inject(app: MainApplication)
}
