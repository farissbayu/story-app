package com.example.submissionuserstoryapp1.ui.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.response.LoginResponse
import com.example.submissionuserstoryapp1.data.remote.response.LoginResult
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class LoginViewModel(private val userRepository: UserRepository): ViewModel() {

    fun userLogin(email: String, password: String): LiveData<LoginResponse> {
        return userRepository.setUserLogin(email, password)
    }

    fun saveToken(token: String) {
        userRepository.setAuthToken(token)
    }

}