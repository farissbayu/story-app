package com.example.submissionuserstoryapp1.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.OnBackPressedCallback
import androidx.activity.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.submissionuserstoryapp1.R
import com.example.submissionuserstoryapp1.database.StoryItem
import com.example.submissionuserstoryapp1.databinding.ActivityMainBinding
import com.example.submissionuserstoryapp1.ui.create.CreateActivity
import com.example.submissionuserstoryapp1.ui.detail.DetailActivity
import com.example.submissionuserstoryapp1.ui.login.LoginActivity
import com.example.submissionuserstoryapp1.ui.maps.MapsActivity

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel: MainViewModel by viewModels { MainViewModelFactory(this) }
    private lateinit var token: String
    private lateinit var adapter: ListStoryAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        adapter = ListStoryAdapter()
        setContentView(binding.root)
//        setupViewModel()
        setupFabAction()
        getListStory()
        setOnBack()
    }

    private fun setOnBack() {
        onBackPressedDispatcher.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                finishAffinity()
                finish()
            }

        })
    }

    private fun setupFabAction() {
        binding.fabAddStory.setOnClickListener {
            val intent = Intent(this@MainActivity, CreateActivity::class.java)
            startActivity(intent)
        }
    }

    private fun getListStory() {
        token = "Bearer ${viewModel.getToken()}"
        val page = 1
        val size = 10
        val location = 1

        viewModel.getListStory(token).observe(this) { stories ->
            adapter.submitData(lifecycle, stories)
        }

        binding.apply {
            rvListStory.layoutManager = LinearLayoutManager(this@MainActivity)
            rvListStory.adapter = adapter
        }

        adapter.setOnItemClickCallback(object : ListStoryAdapter.OnItemClickCallback {
            override fun onItemClicked(data: StoryItem) {
                val intent = Intent(this@MainActivity, DetailActivity::class.java)
                intent.putExtra(DetailActivity.EXTRA_ID, data.id)
                startActivity(intent)
            }

        })
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val authPreferences = AuthPreferences(applicationContext)
//        val userRepository = UserRepository(apiService, authPreferences)
//        val storyRepository = StoryRepository(apiService)
//        viewModel = ViewModelProvider(this, MainViewModelFactory(storyRepository, userRepository)).get(MainViewModel::class.java)
//    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.maps -> {
                val intent = Intent(this@MainActivity, MapsActivity::class.java)
                intent.putExtra(MapsActivity.EXTRA_TOKEN, token)
                startActivity(intent)
                return true
            }
            R.id.logout -> {
                viewModel.clearToken()
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
                return true
            }
            else -> return true
        }
    }
}