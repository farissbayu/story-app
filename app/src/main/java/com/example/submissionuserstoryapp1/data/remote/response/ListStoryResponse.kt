package com.example.submissionuserstoryapp1.data.remote.response

import android.os.Parcelable
import com.example.submissionuserstoryapp1.database.StoryItem
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class ListStoryResponse(

	@field:SerializedName("listStory")
	val listStory: List<ListStoryItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)

data class ListStoryItem(

	@field:SerializedName("photoUrl")
	val photoUrl: String,

	@field:SerializedName("createdAt")
	val createdAt: String,

	@field:SerializedName("name")
	val name: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("lon")
	val lon: Any,

	@field:SerializedName("id")
	val id: String,

	@field:SerializedName("lat")
	val lat: Any
)
