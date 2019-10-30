package com.test.app.di

import com.test.app.App
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AndroidInjectionModule::class,
        ActivityModule::class,
        ApiModule::class,
        FragmentModule::class
    ]
)
interface AppComponent {
    fun injectApplication(app: App)

}
