package com.example.submissionuserstoryapp1.ui.create

import android.app.Instrumentation.ActivityResult
import android.content.Intent
import android.content.Intent.ACTION_GET_CONTENT
import android.content.Intent.EXTRA_INDEX
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.media.ExifInterface
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.core.content.FileProvider
import androidx.lifecycle.ViewModelProvider
import com.example.submissionuserstoryapp1.data.local.AuthPreferences
import com.example.submissionuserstoryapp1.data.remote.retrofit.ApiConfig
import com.example.submissionuserstoryapp1.data.repository.StoryRepository
import com.example.submissionuserstoryapp1.data.repository.UserRepository
import com.example.submissionuserstoryapp1.databinding.ActivityCreateBinding
import com.example.submissionuserstoryapp1.ui.main.MainActivity
import com.example.submissionuserstoryapp1.ui.main.MainViewModel
import com.example.submissionuserstoryapp1.ui.main.MainViewModelFactory
import com.example.submissionuserstoryapp1.util.createCustomTempFile
import com.example.submissionuserstoryapp1.util.reduceFileImage
import com.example.submissionuserstoryapp1.util.rotateFile
import com.example.submissionuserstoryapp1.util.uriToFile
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File

class CreateActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCreateBinding
    private val viewModel: CreateViewModel by viewModels { CreateViewModelFactory(this) }
    private lateinit var token: String
    private var getFile: File? = null
    @RequiresApi(Build.VERSION_CODES.Q)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "Create Story"
        binding = ActivityCreateBinding.inflate(layoutInflater)
        setContentView(binding.root)
//        setupViewModel()
        setupAction()
    }

//    private fun setupViewModel() {
//        val apiService = ApiConfig.getApiService()
//        val authPreferences = AuthPreferences(applicationContext)
//        val userRepository = UserRepository(apiService, authPreferences)
//        val storyRepository = StoryRepository(apiService)
//        viewModel = ViewModelProvider(this, CreateViewModelFactory(storyRepository, userRepository)).get(
//            CreateViewModel::class.java)
//    }

    @RequiresApi(Build.VERSION_CODES.Q)
    private fun setupAction() {
        binding.btnCamera.setOnClickListener {
            startTakePhoto()
        }

        binding.btnGallery.setOnClickListener {
            startIntentGallery()
        }

        binding.btnUpload.setOnClickListener {
            uploadStory()
        }
    }

    private fun uploadStory() {
        var descString = binding.etDescription.text.toString()
        token = "Bearer ${viewModel.getToken()}"
        when {
            descString.isEmpty() -> {
                Toast.makeText(this@CreateActivity, "Silakan tambahkan deskripsi", Toast.LENGTH_SHORT).show()
            }
            getFile == null -> {
                Toast.makeText(this@CreateActivity, "Silakan tambahkan gambar", Toast.LENGTH_SHORT).show()
            }
            else -> {
                val file = reduceFileImage(getFile as File)
                val desc = descString.toRequestBody("text/plain".toMediaType())

                val requestImageFile = file.asRequestBody("image/jpeg".toMediaType())
                val imageMultiPart: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "photo",
                    file.name,
                    requestImageFile
                )
                viewModel.uploadStory(desc, imageMultiPart, null, null, token).observe(this
                ) { responseBody ->
                    if (!responseBody.error) {
                        val intent = Intent(this@CreateActivity, MainActivity::class.java)
                        startActivity(intent)
                        finish()
                    }
                }
            }
        }
    }

    private lateinit var currentPhotoPath: String
    @RequiresApi(Build.VERSION_CODES.Q)
    private val launcherIntentCamera = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == RESULT_OK) {
            val myFile = File(currentPhotoPath)

            val exif = ExifInterface(myFile)
            val orientation = exif.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_UNDEFINED)
            var angle: Float = 0F

            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    angle = 90F
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    angle = 180F
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    angle = 270F
                }
                else -> {
                    angle = 0F
                }
            }

            myFile.let { file ->
                rotateFile(file, angle)
                getFile = file
                binding.ivResult.setImageBitmap(BitmapFactory.decodeFile(file.path))
            }
        }
    }
    @RequiresApi(Build.VERSION_CODES.Q)
    private fun startTakePhoto() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        intent.resolveActivity(packageManager)

        createCustomTempFile(application).also {
            val photoUri: Uri = FileProvider.getUriForFile(
                this@CreateActivity,
                "com.example.submissionuserstoryapp1",
                it
            )
            currentPhotoPath = it.absolutePath
            intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri)
            launcherIntentCamera.launch(intent)
        }
    }

    private val launcherIntentGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result ->
        if (result.resultCode == RESULT_OK) {
            val selectedImage = result.data?.data as Uri
            selectedImage.let { uri ->
                val myFile = uriToFile(uri, this@CreateActivity)
                getFile = myFile
                binding.ivResult.setImageURI(uri)
            }
        }
    }

    private fun startIntentGallery() {
        val intent = Intent()
        intent.action = ACTION_GET_CONTENT
        intent.type = "image/*"
        val chooser = Intent.createChooser(intent, "Choose a Picture")
        launcherIntentGallery.launch(chooser)
    }
}