package com.example.submissionuserstoryapp1.ui.create

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.remote.response.UploadResponse
import com.example.submissionuserstoryapp1.data.repository.StoryRepository
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import okhttp3.MultipartBody
import okhttp3.RequestBody

class CreateViewModel(private val storyRepository: StoryRepository, private val userRepository: UserRepository): ViewModel() {
    fun getToken(): String {
        return userRepository.getAuthToken().toString()
    }

    fun uploadStory(desc: RequestBody, photo: MultipartBody.Part, lat: Float?, lon: Float?, token: String): LiveData<UploadResponse> {
        return storyRepository.uploadStory(desc, photo, lat, lon, token)
    }
}