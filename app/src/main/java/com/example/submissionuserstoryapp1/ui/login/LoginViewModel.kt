package com.example.submissionuserstoryapp1.ui.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.remote.response.LoginResponse
import com.example.submissionuserstoryapp1.data.repository.UserRepository

class LoginViewModel(private val userRepository: UserRepository) : ViewModel() {

    fun userLogin(email: String, password: String): LiveData<LoginResponse> {
        return userRepository.setUserLogin(email, password)
    }

    fun saveToken(token: String) {
        userRepository.setAuthToken(token)
    }

}