package com.example.submissionuserstoryapp1.ui.maps

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.example.submissionuserstoryapp1.R
import com.example.submissionuserstoryapp1.databinding.ActivityMapsBinding
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

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

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