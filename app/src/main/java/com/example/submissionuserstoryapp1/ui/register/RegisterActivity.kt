package com.example.submissionuserstoryapp1.ui.register

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.databinding.ActivityRegisterBinding
import com.example.submissionuserstoryapp1.ui.login.LoginActivity
import com.example.submissionuserstoryapp1.ui.login.LoginViewModel
import com.example.submissionuserstoryapp1.ui.maps.MapsViewModel
import com.example.submissionuserstoryapp1.ui.maps.MapsViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")
class RegisterActivity : AppCompatActivity() {
    private lateinit var binding: ActivityRegisterBinding
    private val viewModel: RegisterViewModel by viewModels { RegisterViewModelFactory(this) }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.hide()
        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupViewModel()
        setupUserInterface()
        setupAction()
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val authPreferences = AuthPreferences(applicationContext)
//        val userRepository = UserRepository(apiService, authPreferences)
//        viewModel = ViewModelProvider(this, RegisterViewModelFactory(userRepository)).get(
//            RegisterViewModel::class.java)
//    }

    private fun setupUserInterface() {
        binding.apply {
            if (etName.text!!.isEmpty()) {
                etName.error = "Nama harus diisi"
            }
            if (etEmail.text!!.isEmpty()) {
                etEmail.error = "Email harus diisi"
            }
            if (etPassword.text!!.isEmpty()) {
                etPassword.error = "Password harus diisi"
            }
        }
    }

    private fun setupAction() {
        binding.apply {
            val name = etName.text
            val email = etEmail.text
            val password = etPassword.text

            btnRegister.setOnClickListener {
                binding.progressBar.visibility = View.VISIBLE
                viewModel.userRegister(name.toString(), email.toString(), password.toString()).observe(this@RegisterActivity) { response ->
                    val intent = Intent(this@RegisterActivity, LoginActivity::class.java)
                    if (!response.error) {
                        startActivity(intent)
                        finish()
                    }
                }

            }
        }
    }
}