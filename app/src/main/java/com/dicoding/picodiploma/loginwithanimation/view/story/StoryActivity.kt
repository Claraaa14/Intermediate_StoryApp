package com.dicoding.picodiploma.loginwithanimation.view.story

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.dicoding.picodiploma.loginwithanimation.data.AdapterStory
import com.dicoding.picodiploma.loginwithanimation.data.pref.UserPreference
import com.dicoding.picodiploma.loginwithanimation.data.pref.dataStore
import com.dicoding.picodiploma.loginwithanimation.databinding.ActivityStoryBinding
import com.dicoding.picodiploma.loginwithanimation.view.ViewModelFactory
import com.dicoding.picodiploma.loginwithanimation.view.addStory.AddStory
import com.dicoding.picodiploma.loginwithanimation.view.login.LoginActivity

class StoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryBinding
    private lateinit var storyViewModel: StoryViewModel
    private lateinit var adapterStory: AdapterStory
    private lateinit var storyPreference: UserPreference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storyPreference = UserPreference.getInstance(dataStore)

        val factory = ViewModelFactory.getInstance(this)
        storyViewModel = ViewModelProvider(this, factory).get(StoryViewModel::class.java)


        buttonAddStory()
        rVStory()
        rVAllStory()

    }

    private fun buttonAddStory(){
        binding.btnAddStory.setOnClickListener{
            val addStory = Intent(this@StoryActivity, AddStory::class.java)
            startActivity(addStory)
        }
    }

    private fun rVStory() {
        binding.storyAppRv.layoutManager = LinearLayoutManager(this)
        adapterStory = AdapterStory()
        binding.storyAppRv.adapter = adapterStory
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun rVAllStory() {

        storyViewModel.getUser().observe(this) { loginResult ->
            Log.d("StoryActivity", "Received token: ${loginResult.token}")
            if (!loginResult.isLogin) {
                val intentStory = Intent(this@StoryActivity, LoginActivity::class.java)
                intentStory.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK //mengatur tumpukan task
                startActivity(intentStory)
            }
            loadingStory(true)
            storyViewModel.setStory(loginResult.token)
        }

        storyViewModel.getStory().observe(this) { stories ->
            if (stories!=null) {
                adapterStory.setStoryList(stories)
                adapterStory.notifyDataSetChanged()
                loadingStory(false)
                Log.d("StoryActivity", "Data dari API berhasil diambil")
            } else {
                loadingStory(false)
            }
        }
    }
    private fun loadingStory(state:Boolean){
        if (state){
            binding.progressHome.visibility = View.VISIBLE
        }else{
            binding.progressHome.visibility= View.GONE
        }
    }
}