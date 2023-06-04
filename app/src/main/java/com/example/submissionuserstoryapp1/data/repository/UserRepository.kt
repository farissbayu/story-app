package com.example.submissionuserstoryapp1.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.response.LoginResponse
import com.example.submissionuserstoryapp1.data.remote.response.RegisterResponse
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserRepository(
    private val apiService: ApiService,
    private val authPreferences: AuthPreferences
) {

    fun setUserLogin(email: String, password: String): LiveData<LoginResponse> {
        val loginResponse = MutableLiveData<LoginResponse>()
        val client = apiService.userLogin(email, password)
        client.enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        loginResponse.value = responseBody!!
                    }
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Log.e("UserRepository", "onFailure: ${t.message}")
            }

        })
        return loginResponse
    }

    fun setUserRegister(name: String, email: String, password: String): LiveData<RegisterResponse> {
        val registerResponse = MutableLiveData<RegisterResponse>()
        val client = apiService.userRegister(name, email, password)
        client.enqueue(object : Callback<RegisterResponse> {
            override fun onResponse(
                call: Call<RegisterResponse>,
                response: Response<RegisterResponse>
            ) {
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        registerResponse.value = responseBody!!
                    }
                }
            }

            override fun onFailure(call: Call<RegisterResponse>, t: Throwable) {
                Log.e("UserRepository", "onFailure: ${t.message}")
            }

        })
        return registerResponse
    }

    fun setAuthToken(token: String) {
        authPreferences.setToken(token)
    }

    fun getAuthToken(): String? {
        return authPreferences.getToken()
    }

    fun clearAuthToken() {
        authPreferences.clearToken()
    }
}