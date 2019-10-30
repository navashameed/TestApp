package com.test.app.di

import androidx.lifecycle.ViewModel
import com.test.app.network.UserAlbumApi
import com.test.app.di.annotation.PerFragment
import com.test.app.interactor.UserInteractor
import com.test.app.viewmodel.UserViewModel
import com.test.app.util.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoMap
import retrofit2.Retrofit

@Module(includes = [UserModule.BindsModule::class])
class UserModule {

    @Provides
    @PerFragment
    fun providesUserInteractor(userAlbumApi: UserAlbumApi): UserInteractor {
        return UserInteractor(userAlbumApi)
    }

    @Provides
    @PerFragment
    fun providesUserAlbumApi(retrofit: Retrofit): UserAlbumApi {
        return retrofit.create(UserAlbumApi::class.java)
    }

    @Module
    abstract class BindsModule {
        @Binds
        @IntoMap
        @ViewModelKey(UserViewModel::class)
        abstract fun bindUserViewModel(viewViewModel: UserViewModel): ViewModel
    }

}
