package com.agusw.test_list_games.di

import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object MoshiModule {

    @Provides
    fun instance(): Moshi = Moshi.Builder().build()
}