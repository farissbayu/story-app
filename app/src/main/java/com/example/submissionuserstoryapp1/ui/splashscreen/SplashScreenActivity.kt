package com.example.submissionuserstoryapp1.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.databinding.ActivitySplashScreenBinding
import com.example.submissionuserstoryapp1.ui.login.LoginActivity
import com.example.submissionuserstoryapp1.ui.main.MainActivity
import com.example.submissionuserstoryapp1.ui.maps.MapsViewModel
import com.example.submissionuserstoryapp1.ui.maps.MapsViewModelFactory

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels { SplashScreenViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)
//        setupViewModel()
        setupUserFlow()
    }

    private fun setupUserFlow() {
        var tokenIsEmpty = true
        viewModel.token.observe(this) { token ->
            if (!token.isNullOrEmpty()) {
                tokenIsEmpty = false
            }
        }
        Handler(Looper.getMainLooper()).postDelayed({
            if (tokenIsEmpty) {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            } else {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        }, 3000)
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val authPreferences = AuthPreferences(this)
//        val userRepository = UserRepository(apiService, authPreferences)
//        viewModel = ViewModelProvider(this, SplashScreenViewModelFactory(userRepository)).get(
//            SplashScreenViewModel::class.java)
//    }
}