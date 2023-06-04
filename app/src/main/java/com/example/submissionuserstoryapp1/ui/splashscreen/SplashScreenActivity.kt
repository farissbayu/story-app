package com.example.submissionuserstoryapp1.ui.splashscreen

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionuserstoryapp1.databinding.ActivitySplashScreenBinding
import com.example.submissionuserstoryapp1.ui.login.LoginActivity
import com.example.submissionuserstoryapp1.ui.main.MainActivity

@SuppressLint("CustomSplashScreen")
class SplashScreenActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashScreenBinding
    private val viewModel: SplashScreenViewModel by viewModels { SplashScreenViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivitySplashScreenBinding.inflate(layoutInflater)

        setContentView(binding.root)
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
}