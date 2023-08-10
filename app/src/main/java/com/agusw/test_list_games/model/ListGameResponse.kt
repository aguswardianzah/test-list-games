package com.agusw.test_list_games.model

import androidx.annotation.Keep
import com.agusw.test_list_games.db.entities.Games
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@Keep
@JsonClass(generateAdapter = true)
data class ListGameResponse(
    val count: Int = 0,
    val results: List<Games> = emptyList()
)

@Keep
@JsonClass(generateAdapter = true)
data class GameDetailResponse(
    val id: Int = 0,
    val name: String = "",
    val description: String = "",
    val rating: Float = 0f,
    @Json(name = "background_image") val image: String = "",
    val publishers: List<Publisher> = emptyList(),
    val released: String = "",
    val playtime: Float = 0f
)

@Keep
@JsonClass(generateAdapter = true)
data class Publisher(
    val name: String = ""
)