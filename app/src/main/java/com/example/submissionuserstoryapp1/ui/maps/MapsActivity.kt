package com.example.submissionuserstoryapp1.ui.maps

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.ViewModelProvider
import com.example.submissionuserstoryapp1.R
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.StoryRepository
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.databinding.ActivityMapsBinding
import com.example.submissionuserstoryapp1.ui.detail.DetailViewModel
import com.example.submissionuserstoryapp1.ui.detail.DetailViewModelFactory
import com.example.submissionuserstoryapp1.ui.main.MainViewModel
import com.example.submissionuserstoryapp1.ui.main.MainViewModelFactory
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {
    companion object {
        const val EXTRA_TOKEN = "extra_token"
    }

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var token: String
    private val viewModel: MapsViewModel by viewModels { MapsViewModelFactory(this) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        token = intent.getStringExtra(EXTRA_TOKEN).toString()
        setContentView(binding.root)
//        setupViewModel()

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val storyRepository = StoryRepository(apiService)
//        viewModel = ViewModelProvider(this, MapsViewModelFactory(storyRepository)).get(
//            MapsViewModel::class.java)
//    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Map control
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isIndoorLevelPickerEnabled = true
        mMap.uiSettings.isCompassEnabled = true
        mMap.uiSettings.isMapToolbarEnabled = true

        setLocationMarker()
    }

    private fun setLocationMarker() {
        val location = 1
        viewModel.getListStoryWithLocation(null, 30, location, token).observe(this) { stories ->
            for (story in stories) {
                val lat: Double = story.lat as Double
                val lon: Double = story.lon as Double
                val position = LatLng(lat, lon)
                mMap.addMarker(MarkerOptions().position(position).title(story.name))
                mMap.moveCamera(CameraUpdateFactory.newLatLng(position))
            }
        }
    }
}