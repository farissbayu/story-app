package com.example.submissionuserstoryapp1.data.local

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiService
import com.example.submissionuserstoryapp1.database.StoryDatabase
import com.example.submissionuserstoryapp1.database.StoryItem

@OptIn(ExperimentalPagingApi::class)
class StoryRemoteMediator(
    private val database: StoryDatabase,
    private val apiService: ApiService,
    private val token: String
) : RemoteMediator<Int, StoryItem>() {
    private companion object {
        const val INITIAL_PAGE_INDEX = 1
    }

    override suspend fun initialize(): InitializeAction {
        return InitializeAction.LAUNCH_INITIAL_REFRESH
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, StoryItem>
    ): MediatorResult {
        val page = INITIAL_PAGE_INDEX

        try {
            val responseData = apiService.getListStory(page, state.config.pageSize, 1, token)
            var endOfPaginationReached = responseData.listStory.isEmpty()
            var listStory: List<StoryItem> = responseData.listStory.map {
                StoryItem(
                    it.id, it.photoUrl, it.name
                )
            }
            database.withTransaction {
                if (loadType == LoadType.REFRESH) {
                    database.storyDao().deleteAll()
                }
                database.storyDao().insertStory(listStory)
            }
            return MediatorResult.Success(endOfPaginationReached = endOfPaginationReached)
        } catch (exception: Exception) {
            return MediatorResult.Error(exception)
        }
    }
}