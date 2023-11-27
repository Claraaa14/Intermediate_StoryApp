package com.dicoding.picodiploma.loginwithanimation.retrofit

import com.dicoding.picodiploma.loginwithanimation.response.AllStoriesResponse
import com.dicoding.picodiploma.loginwithanimation.response.FileUploadResponse
import com.dicoding.picodiploma.loginwithanimation.response.LoginResponse
import com.dicoding.picodiploma.loginwithanimation.response.RegisterResponse
import com.dicoding.picodiploma.loginwithanimation.view.login.UserLogin
import com.dicoding.picodiploma.loginwithanimation.view.signup.UserRegister
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface ApiService {

    @GET("stories")
    fun getStory(
        @Header("Authorization") token: String
    ): Call<AllStoriesResponse>

    @POST("login")
    fun loginStoryApp(
        @Body login : UserLogin
    ): Call<LoginResponse>

    @POST("register")
    fun registerStoryApp(
        @Body register : UserRegister
    ):Call<RegisterResponse>

    @Multipart
    @POST("stories/guest")
    suspend fun uploadImage(
        @Part file: MultipartBody.Part,
        @Part("description") description: RequestBody,
    ): FileUploadResponse
}