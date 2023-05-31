package com.example.submissionuserstoryapp1.ui.detail

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.bumptech.glide.Glide
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.StoryRepository
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.databinding.ActivityDetailBinding
import com.example.submissionuserstoryapp1.ui.create.CreateViewModel
import com.example.submissionuserstoryapp1.ui.create.CreateViewModelFactory

class DetailActivity : AppCompatActivity() {
    companion object {
        const val EXTRA_ID = "extra_id"
    }

    private lateinit var binding: ActivityDetailBinding
    private val viewModel: DetailViewModel by viewModels { DetailViewModelFactory(this) }
    private lateinit var token: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        supportActionBar?.title = "Detail Story"
        setContentView(binding.root)
        val id = intent.getStringExtra(EXTRA_ID).toString()
//        setupViewModel()
        getToken()
        getDetailStory(id, token)
        playAnimation()
    }

    private fun playAnimation() {
        val image = ObjectAnimator.ofFloat(binding.ivDetailStory, View.ALPHA, 1F).setDuration(2500)
        val name = ObjectAnimator.ofFloat(binding.tvNameDetailStory, View.ALPHA, 1F).setDuration(2500)
        val divider = ObjectAnimator.ofFloat(binding.divider, View.ALPHA, 1F).setDuration(2500)
        val desc = ObjectAnimator.ofFloat(binding.tvDescDetailStory, View.ALPHA, 1F).setDuration(2500)

        AnimatorSet().apply {
            playSequentially(image, name, divider, desc)
            start()
        }
    }

    private fun getToken() {
        token = "Bearer ${viewModel.getToken()}"
    }

    private fun getDetailStory(id: String, token: String) {
        viewModel.getStory(id, token).observe(this) { detailStory ->
            binding.apply {
                Glide.with(this@DetailActivity)
                    .load(detailStory.photoUrl)
                    .into(ivDetailStory)
                tvNameDetailStory.text = detailStory.name
                tvDescDetailStory.text = detailStory.description
            }
        }
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val authPreferences = AuthPreferences(applicationContext)
//        val userRepository = UserRepository(apiService, authPreferences)
//        val storyRepository = StoryRepository(apiService)
//        viewModel = ViewModelProvider(this, DetailViewModelFactory(userRepository, storyRepository)).get(
//            DetailViewModel::class.java)
//    }
}