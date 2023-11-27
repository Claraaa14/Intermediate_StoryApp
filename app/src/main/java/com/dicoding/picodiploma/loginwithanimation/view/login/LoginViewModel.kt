package com.dicoding.picodiploma.loginwithanimation.view.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dicoding.picodiploma.loginwithanimation.data.UserRepository
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.response.LoginResult
import kotlinx.coroutines.launch

class LoginViewModel(private val userPreference: UserPreference) : ViewModel() {

    fun saveSession(loginResult: LoginResult) {
        viewModelScope.launch {
            userPreference.saveSession(loginResult)
        }
    }
}