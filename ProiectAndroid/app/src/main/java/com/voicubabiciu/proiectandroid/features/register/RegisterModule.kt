package com.voicubabiciu.proiectandroid.features.register

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.FragmentComponent

@Module
@InstallIn(FragmentComponent::class)
object RegisterModule {

    @Provides
    fun provideRegisterRepository(): RegisterRepository {
        return RegisterRepository()
    }
}