package com.ancafarcas.proiectandroid.features.home.addToDo

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object AddToDoModule {

    @Provides
    fun provideAddToDoRepository(): AddToDoRepository {
        return AddToDoRepository()
    }
}