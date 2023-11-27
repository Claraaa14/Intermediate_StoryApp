package com.dicoding.picodiploma.loginwithanimation.response

data class AllStoriesResponse(
    val error: Boolean,
    val listStory: ArrayList<Story>,
    val message: String
)