package com.ancafarcas.proiectandroid.features.login

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object LoginModule {

    @Provides
    fun provideLoginRepository(): LoginRepository {
        return LoginRepository()
    }
}