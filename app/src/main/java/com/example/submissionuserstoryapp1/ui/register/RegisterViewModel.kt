package com.example.submissionuserstoryapp1.ui.register

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.remote.response.RegisterResponse
import com.example.submissionuserstoryapp1.data.repository.UserRepository


class RegisterViewModel(private val userRepository: UserRepository) : ViewModel() {
    fun userRegister(name: String, email: String, password: String): LiveData<RegisterResponse> {
        return userRepository.setUserRegister(name, email, password)
    }
}