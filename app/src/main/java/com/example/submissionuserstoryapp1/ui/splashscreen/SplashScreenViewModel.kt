package com.example.submissionuserstoryapp1.ui.splashscreen

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.repository.UserRepository

class SplashScreenViewModel(private val userRepository: UserRepository) : ViewModel() {
    private val _token = MutableLiveData<String>()
    val token: LiveData<String> = _token

    init {
        _token.value = userRepository.getAuthToken()
    }
}