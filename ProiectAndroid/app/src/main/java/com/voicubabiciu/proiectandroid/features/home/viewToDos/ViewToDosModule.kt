package com.voicubabiciu.proiectandroid.features.home.viewToDos

import com.voicubabiciu.proiectandroid.features.home.addToDo.AddToDoRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object ViewToDosModule {
    @Provides
    fun provideViewToDosRepository(): ViewTodosRepository {
        return ViewTodosRepository()
    }
}