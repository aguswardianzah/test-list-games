package com.agusw.test_list_games.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agusw.test_list_games.db.entities.Fav
import com.agusw.test_list_games.db.entities.Games

@Dao
interface FavDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: Fav)

    @Query("select * from fav")
    fun pagingSource(): PagingSource<Int, Games>

    @Query("select count(id) from fav")
    suspend fun count(): Int

    @Query("select count(id) from fav where gid = :id")
    suspend fun count(id: Int): Int

    @Query("delete from fav where gid = :id")
    suspend fun delete(id: Int)

    @Query("delete from fav")
    suspend fun nuke()
}