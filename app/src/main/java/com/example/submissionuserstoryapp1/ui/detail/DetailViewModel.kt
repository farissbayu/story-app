package com.example.submissionuserstoryapp1.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.remote.response.DetailStoryResponse
import com.example.submissionuserstoryapp1.data.remote.response.ListStoryItem
import com.example.submissionuserstoryapp1.data.remote.response.Story
import com.example.submissionuserstoryapp1.data.repository.StoryRepository
import com.example.submissionuserstoryapp1.data.repository.UserRepository

class DetailViewModel(private val userRepository: UserRepository, private val storyRepository: StoryRepository): ViewModel() {
    fun getStory(id: String, token: String): LiveData<Story> {
        return storyRepository.getDetailStory(id, token)
    }

    fun getToken(): String {
        return userRepository.getAuthToken().toString()
    }
}