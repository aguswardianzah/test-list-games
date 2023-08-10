package com.agusw.test_list_games.api

import com.agusw.test_list_games.model.GameDetailResponse
import com.agusw.test_list_games.model.ListGameResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @GET("games")
    suspend fun games(
        @Query("search") search: String? = "",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
    ): ListGameResponse

    @GET("games/{id}")
    suspend fun detail(@Path("id") id: Int): GameDetailResponse
}