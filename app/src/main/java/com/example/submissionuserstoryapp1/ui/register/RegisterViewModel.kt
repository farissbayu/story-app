package com.example.submissionuserstoryapp1.ui.register

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.submissionuserstoryapp1.data.remote.response.RegisterResponse
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class RegisterViewModel(private val userRepository: UserRepository): ViewModel() {
    fun userRegister(name: String, email: String, password: String): LiveData<RegisterResponse> {
        return userRepository.setUserRegister(name, email, password)
    }
}