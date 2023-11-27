package com.dicoding.picodiploma.loginwithanimation.view.addStory

import androidx.lifecycle.ViewModel
import com.dicoding.picodiploma.loginwithanimation.di.ResultState
import com.dicoding.picodiploma.loginwithanimation.response.FileUploadResponse
import com.dicoding.picodiploma.loginwithanimation.retrofit.ApiConfig
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.HttpException
import java.io.File

class AddStoryViewModel : ViewModel() {
    private val apiService = ApiConfig.getApiService()

    suspend fun uploadImageToServer(imageFile: File, description: String): ResultState<FileUploadResponse> {
        return try {

            val requestBody = description.toRequestBody("text/plain".toMediaType())
            val requestImageFile = imageFile.asRequestBody("image/jpeg".toMediaType())
            val multipartBody = MultipartBody.Part.createFormData(
                "photo",
                imageFile.name,
                requestImageFile
            )
            val successResponse = apiService.uploadImage(multipartBody, requestBody)
            ResultState.Success(successResponse)
        } catch (e: HttpException) {
            val errorBody = e.response()?.errorBody()?.string()
            val errorResponse = Gson().fromJson(errorBody, FileUploadResponse::class.java)
            ResultState.Error(errorResponse.message)
        }
    }
}