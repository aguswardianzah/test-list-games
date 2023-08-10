package com.agusw.test_list_games

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.agusw.test_list_games.db.GamesDB
import com.agusw.test_list_games.db.dao.FavDao
import com.agusw.test_list_games.db.dao.GamesDao
import com.agusw.test_list_games.db.entities.Fav
import com.agusw.test_list_games.db.entities.Games
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DbTest {

    private lateinit var db: GamesDB
    private lateinit var gameDao: GamesDao
    private lateinit var favDao: FavDao

    @Before
    fun createDb() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            GamesDB::class.java
        ).build()

        gameDao = db.games()
        favDao = db.fav()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() = db.close()

    @Test
    fun insertGame() = runBlocking {
        val items = listOf(
            Games(0, 1, "test-img", "movie-1", "2021-01-01", 4.5f),
            Games(0, 2, "test-img", "movie-2", "2021-01-01", 4.5f),
        )

        gameDao.insert(items)

        assert(gameDao.count("") == 2)
    }

    @Test
    fun deleteAllGame() = runBlocking {
        val items = listOf(
            Games(0, 1, "test-img", "movie-1", "2021-01-01", 4.5f),
            Games(0, 2, "test-img", "movie-2", "2021-01-01", 4.5f),
        )

        gameDao.insert(items)
        gameDao.nuke()

        assert(gameDao.count("") == 0)
    }

    @Test
    fun insertFav() = runBlocking {
        val fav = Fav(0, 1, "test-img", "movie-1", "2021-01-01", 4.5f)

        favDao.insert(fav)

        assert(favDao.count(1) == 1)
    }

    @Test
    fun deleteFav() = runBlocking {
        val fav = Fav(0, 1, "test-img", "movie-1", "2021-01-01", 4.5f)

        favDao.insert(fav)
        favDao.delete(1)

        assert(favDao.count(1) == 0)
    }
}