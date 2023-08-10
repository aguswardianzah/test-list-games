package com.agusw.test_list_games.di

import android.content.Context
import androidx.room.Room
import com.agusw.test_list_games.db.GamesDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DbModule {

    @Provides
    fun provideDb(@ApplicationContext context: Context): GamesDB =
        Room.databaseBuilder(context, GamesDB::class.java, "games.db")
            .fallbackToDestructiveMigration()
            .build()
}