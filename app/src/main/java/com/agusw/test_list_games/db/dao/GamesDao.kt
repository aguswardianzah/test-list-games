package com.agusw.test_list_games.db.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.agusw.test_list_games.db.entities.Games

@Dao
interface GamesDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(data: List<Games>)

    @Query("select * from games where name like '%' || :query || '%'")
    fun pagingSource(query: String?): PagingSource<Int, Games>

    @Query("select count(id) from games where name like '%' || :search || '%'")
    suspend fun count(search: String?): Int

    @Query("delete from games")
    suspend fun nuke()
}