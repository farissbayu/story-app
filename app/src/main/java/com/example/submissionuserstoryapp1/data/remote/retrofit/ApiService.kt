package com.example.submissionuserstoryapp1.data.remote.retrofit

import com.example.submissionuserstoryapp1.data.remote.response.*
import com.example.submissionuserstoryapp1.database.StoryItem
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @FormUrlEncoded
    @POST("register")
    fun userRegister(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<RegisterResponse>

    @FormUrlEncoded
    @POST("login")
    fun userLogin(
        @Field("email") email: String,
        @Field("password") password: String
    ): Call<LoginResponse>

    @GET("stories")
    suspend fun getListStory(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null,
        @Header("Authorization") token: String
    ): ListStoryResponse

    @GET("stories")
    fun getListStoryWithLocation(
        @Query("page") page: Int? = null,
        @Query("size") size: Int? = null,
        @Query("location") location: Int? = null,
        @Header("Authorization") token: String
    ): Call<ListStoryResponse>

    @GET("stories/{id}")
    fun getDetailStory(
        @Path("id") id: String,
        @Header("Authorization") token: String
    ): Call<DetailStoryResponse>

    @Multipart
    @POST("stories")
    fun uploadStory(
        @Part("description") description: RequestBody,
        @Part photo: MultipartBody.Part,
        @Part("lat") lat: Float?,
        @Part("lon") lon: Float?,
        @Header("Authorization") token: String
    ): Call<UploadResponse>
}