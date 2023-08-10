package com.agusw.test_list_games.db.entities

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json

@Entity(tableName = "fav", indices = [Index(value = ["id"], unique = true)])
data class Fav(
    @Json(ignore = true) @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Json(name = "id") val gid: Int = 0,
    val image: String = "",
    val name: String = "",
    val released: String = "",
    val rating: Float = 0f,
)