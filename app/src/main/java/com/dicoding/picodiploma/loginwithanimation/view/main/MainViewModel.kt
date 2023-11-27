package com.dicoding.picodiploma.loginwithanimation.view.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.response.LoginResult
import kotlinx.coroutines.launch

class MainViewModel(private val userPreference: UserPreference) : ViewModel() {
    fun getSession(): LiveData<LoginResult> {
        return userPreference.getSession().asLiveData()
    }

    fun logout() {
        viewModelScope.launch {
            userPreference.logout()
        }
    }

}