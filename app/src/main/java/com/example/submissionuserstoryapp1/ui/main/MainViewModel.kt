package com.example.submissionuserstoryapp1.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.example.submissionuserstoryapp1.data.repository.StoryRepository
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.database.StoryItem

class MainViewModel(
    private val storyRepository: StoryRepository,
    private val userRepository: UserRepository
) : ViewModel() {
    fun getListStory(token: String): LiveData<PagingData<StoryItem>> {
        return storyRepository.getListStory(token).cachedIn(viewModelScope)
    }

    fun getToken(): String {
        return userRepository.getAuthToken().toString()
    }

    fun clearToken() {
        userRepository.clearAuthToken()
    }
}