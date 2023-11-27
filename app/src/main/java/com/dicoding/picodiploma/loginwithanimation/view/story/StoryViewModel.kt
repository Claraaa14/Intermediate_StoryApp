package com.dicoding.picodiploma.loginwithanimation.view.story

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.response.AllStoriesResponse
import com.dicoding.picodiploma.loginwithanimation.response.LoginResult
import com.dicoding.picodiploma.loginwithanimation.response.Story
import com.dicoding.picodiploma.loginwithanimation.retrofit.ApiConfig
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class StoryViewModel (private val userPreference: UserPreference) : ViewModel(){
    val storyList = MutableLiveData<ArrayList<Story>?>()

    fun setStory(token : String) {
        ApiConfig.getApiService().getStory(token)
            .enqueue(object : Callback<AllStoriesResponse> {
                override fun onResponse(
                    call: Call<AllStoriesResponse>,
                    response: Response<AllStoriesResponse>
                ) {
                    if (response.isSuccessful) {
                        storyList.postValue(response.body()?.listStory)
                    } else {
                        storyList.postValue(null)
                    }
                }

                override fun onFailure(call: Call<AllStoriesResponse>, t: Throwable) {
                    Log.e("StoryViewModel", "Permintaan API gagal: ${t.message}")
                    storyList.postValue(null)
                }
            })

    }

    fun getStory() : MutableLiveData<ArrayList<Story>?>{
        return storyList
    }

    fun getUser() : LiveData<LoginResult> {
        return userPreference.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPreference.logout()
        }
    }
}