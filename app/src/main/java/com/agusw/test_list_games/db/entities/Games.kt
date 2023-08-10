package com.agusw.test_list_games.db.entities

import androidx.annotation.Keep
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
@Entity(tableName = "games", indices = [Index(value = ["id"], unique = true)])
data class Games(
    @Json(ignore = true) @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @Json(name = "id") val gid: Int = 0,
    @Json(name = "background_image") val image: String = "",
    val name: String = "",
    val released: String = "",
    val rating: Float = 0f,
)