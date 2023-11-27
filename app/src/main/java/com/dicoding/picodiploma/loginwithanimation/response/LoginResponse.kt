package com.dicoding.picodiploma.loginwithanimation.response

data class LoginResponse(
    val error: Boolean,
    val loginResult: LoginResult,
    val message: String
)