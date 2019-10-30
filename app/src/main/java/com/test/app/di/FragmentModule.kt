package com.test.app.di

import com.test.app.di.annotation.PerFragment
import com.test.app.view.fragment.AlbumListFragment
import com.test.app.view.fragment.UserListFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {
    @PerFragment
    @ContributesAndroidInjector(modules = [UserModule::class])
    abstract fun bindListFragment(): UserListFragment

    @PerFragment
    @ContributesAndroidInjector(modules = [UserModule::class])
    abstract fun bindAlbumFragment(): AlbumListFragment
}
