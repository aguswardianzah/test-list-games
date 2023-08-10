package com.agusw.test_list_games.vm

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.paging.cachedIn
import androidx.room.withTransaction
import com.agusw.test_list_games.api.ApiService
import com.agusw.test_list_games.db.GamesDB
import com.agusw.test_list_games.db.entities.Fav
import com.agusw.test_list_games.db.entities.Games
import com.agusw.test_list_games.model.GameDetailResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.launch
import timber.log.Timber
import javax.inject.Inject

@HiltViewModel
class GameViewModel @Inject constructor(
    private val db: GamesDB,
    private val api: ApiService
) : ViewModel() {

    val search = MutableStateFlow("")
    var itemCount = 0

    @OptIn(ExperimentalPagingApi::class)
    private fun remoteMediator(search: String?): RemoteMediator<Int, Games> {
        return object : RemoteMediator<Int, Games>() {
            override suspend fun load(
                loadType: LoadType,
                state: PagingState<Int, Games>
            ): MediatorResult {
                return try {
                    val loadKey = when (loadType) {
                        LoadType.REFRESH -> 1
                        LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
                        LoadType.APPEND -> (db.games().count(search) / state.config.pageSize) + 1
                    }

                    val response = api.games(search, loadKey, state.config.pageSize)
                    itemCount = response.count

                    db.withTransaction {
                        if (loadType == LoadType.REFRESH) db.games().nuke()

                        db.games().insert(response.results)
                    }

                    MediatorResult.Success(itemCount < db.games().count(search))
                } catch (e: Exception) {
                    Timber.e(e)
                    MediatorResult.Error(e)
                }
            }

            override suspend fun initialize(): InitializeAction =
                InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    @OptIn(ExperimentalPagingApi::class, ExperimentalCoroutinesApi::class)
    val gamePager by lazy {
        search.flatMapLatest {
            Pager(
                config = PagingConfig(pageSize = 10, initialLoadSize = 1),
                remoteMediator = remoteMediator(it)
            ) {
                db.games().pagingSource(it)
            }.flow
        }
    }

    val favPager by lazy {
        Pager(PagingConfig(pageSize = 10)) { db.fav().pagingSource() }.flow
    }

    val detail by lazy { MutableLiveData<GameDetailResponse>() }
    val isFavorite by lazy { MutableLiveData<Boolean>() }

    fun getDetail(id: Int) {
        viewModelScope.launch {
            isFavorite.postValue(db.fav().count(id) > 0)
            detail.postValue(api.detail(id))
        }
    }

    fun toggleFavorite(value: Boolean, data: GameDetailResponse) {
        viewModelScope.launch {
            if (value)
                db.fav().insert(Fav(0, data.id, data.image, data.name, data.released, data.rating))
            else
                db.fav().delete(data.id)

            favPager
        }
    }
}