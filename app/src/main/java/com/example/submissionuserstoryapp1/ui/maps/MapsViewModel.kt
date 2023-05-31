package com.example.submissionuserstoryapp1.ui.maps

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionuserstoryapp1.data.remote.response.ListStoryItem
import com.example.submissionuserstoryapp1.data.repository.StoryRepository

class MapsViewModel(private val storyRepository: StoryRepository): ViewModel() {
//    private val _story = MutableLiveData<List<StoryItem>>()
//    var story: LiveData<List<StoryItem>> = _story

    fun getListStoryWithLocation(page: Int?, size: Int?, location: Int?, token: String): LiveData<List<ListStoryItem>> {
        return storyRepository.getListStoryWithLocation(page, size, location, token)
    }
}