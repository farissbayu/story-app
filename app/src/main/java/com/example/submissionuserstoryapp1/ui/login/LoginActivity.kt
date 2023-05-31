package com.example.submissionuserstoryapp1.ui.login

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionuserstoryapp1.ui.main.MainActivity
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.databinding.ActivityLoginBinding
import com.example.submissionuserstoryapp1.ui.detail.DetailViewModel
import com.example.submissionuserstoryapp1.ui.detail.DetailViewModelFactory
import com.example.submissionuserstoryapp1.ui.register.RegisterActivity

class LoginActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }
    private lateinit var binding: ActivityLoginBinding
    private val viewModel: LoginViewModel by viewModels { LoginViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupViewModel()
        setupUserInterface()
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
                finish()
            }

        })
        setupAction()
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val authPreferences = AuthPreferences(applicationContext)
//        val userRepository = UserRepository(apiService, authPreferences)
//        viewModel = ViewModelProvider(this, LoginViewModelFactory(userRepository)).get(LoginViewModel::class.java)
//    }

    private fun setupAction() {
        binding.apply {
            etEmail.setText("")
            etPassword.setText("")
            val email = etEmail.text
            val password = etPassword.text
            var token = ""

            btnLogin.setOnClickListener {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.userLogin(email.toString(), password.toString()).observe(this@LoginActivity) { response ->
                    token = response.loginResult.token
                    if (!response.error) {
                        val intent = Intent(this@LoginActivity, MainActivity::class.java)
                        intent.putExtra("token", token)
                        viewModel.saveToken(token)
                        startActivity(intent)
                        finish()
                    }
                }
            }

            btnSignup.setOnClickListener {
                val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun setupUserInterface() {
        binding.apply {
            if (etEmail.text!!.isEmpty()) {
                etEmail.error = "Email harus diisi"
            }
            if (etPassword.text!!.isEmpty()) {
                etPassword.error = "Password harus diisi"
            }
        }
    }
}
