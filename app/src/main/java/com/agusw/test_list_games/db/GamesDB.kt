package com.agusw.test_list_games.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.agusw.test_list_games.db.dao.FavDao
import com.agusw.test_list_games.db.dao.GamesDao
import com.agusw.test_list_games.db.entities.Fav
import com.agusw.test_list_games.db.entities.Games

@Database(version = 7, entities = [Games::class, Fav::class], exportSchema = false)
abstract class GamesDB : RoomDatabase() {

    abstract fun games(): GamesDao
    abstract fun fav(): FavDao
}