package com.dicoding.picodiploma.loginwithanimation.view.detail

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityDetailStoryBinding
import com.dicoding.picodiploma.loginwithanimation.response.Story
import com.dicoding.picodiploma.loginwithanimation.view.addStory.AddStory

class DetailStory : AppCompatActivity() {

    private lateinit var binding: ActivityDetailStoryBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        buttonDetail()
        viewDetailStory()
    }

    private fun buttonDetail() {
        binding.btnAddStory.setOnClickListener {
            val view = Intent(this@DetailStory, AddStory::class.java)
            startActivity(view)
        }
    }

    private fun viewDetailStory() {
        val detailStory = intent.getParcelableExtra<Story>("storyData") as Story
        binding.nameUser.text = detailStory.name
        binding.description.text = detailStory.description

        Glide.with(this)
            .load(detailStory.photoUrl)
            .centerCrop()
            .into(binding.image)
    }
}