package com.example.submissionuserstoryapp1.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.*
import com.example.submissionuserstoryapp1.data.local.StoryRemoteMediator
import com.example.submissionuserstoryapp1.data.remote.response.*
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiService
import com.example.submissionuserstoryapp1.database.StoryDatabase
import com.example.submissionuserstoryapp1.database.StoryItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryRepository(
    private val apiService: ApiService,
    private val database: StoryDatabase
) {

    fun getListStory(token: String): LiveData<PagingData<StoryItem>> {
        @OptIn(ExperimentalPagingApi::class)
        return Pager(
            config = PagingConfig(
                pageSize = 5
            ),
            remoteMediator = StoryRemoteMediator(database, apiService, token),
            pagingSourceFactory = {
                database.storyDao().getAllStory()
            }
        ).liveData
    }

    private val listStory = MutableLiveData<List<ListStoryItem>>()
    fun getListStoryWithLocation(
        page: Int?,
        size: Int?,
        location: Int?,
        token: String
    ): LiveData<List<ListStoryItem>> {
        val client = apiService.getListStoryWithLocation(page, size, location, token)
        client.enqueue(object : Callback<ListStoryResponse> {
            override fun onResponse(
                call: Call<ListStoryResponse>,
                response: Response<ListStoryResponse>
            ) {
                if (response.isSuccessful) {
                    listStory.postValue(response.body()!!.listStory)
                }
            }

            override fun onFailure(call: Call<ListStoryResponse>, t: Throwable) {
                Log.e("StoryWithLocation", t.message.toString())
            }
        })
        return listStory
    }

    private val detailStory = MutableLiveData<Story>()
    fun getDetailStory(id: String, token: String): LiveData<Story> {
        val client = apiService.getDetailStory(id, token)
        client.enqueue(object : Callback<DetailStoryResponse> {
            override fun onResponse(
                call: Call<DetailStoryResponse>,
                response: Response<DetailStoryResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        detailStory.value = responseBody.story
                    }
                }
            }

            override fun onFailure(call: Call<DetailStoryResponse>, t: Throwable) {
                Log.e("StoryRepositoryFailed", "$ { t.message }")
            }
        })
        return detailStory
    }

    fun uploadStory(
        desc: RequestBody,
        photo: MultipartBody.Part,
        lat: Float?,
        lon: Float?,
        token: String
    ): LiveData<UploadResponse> {
        val responseBody = MutableLiveData<UploadResponse>()
        val client = apiService.uploadStory(desc, photo, lat, lon, token)
        client.enqueue(object : Callback<UploadResponse> {
            override fun onResponse(
                call: Call<UploadResponse>,
                response: Response<UploadResponse>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        responseBody.value = response.body()
                    }
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.e("StoryRepositoryFailure", "${t.message}")
            }

        })
        return responseBody
    }
}