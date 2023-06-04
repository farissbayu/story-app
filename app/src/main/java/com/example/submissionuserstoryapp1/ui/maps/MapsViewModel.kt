package com.example.submissionuserstoryapp1.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.remote.response.ListStoryItem
import com.example.submissionuserstoryapp1.data.repository.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository) : ViewModel() {

    fun getListStoryWithLocation(
        page: Int?,
        size: Int?,
        location: Int?,
        token: String
    ): LiveData<List<ListStoryItem>> {
        return storyRepository.getListStoryWithLocation(page, size, location, token)
    }
}