package com.dicoding.picodiploma.loginwithanimation.data

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.dicoding.picodiploma.loginwithanimation.databinding.StoryItemBinding
import com.dicoding.picodiploma.loginwithanimation.response.Story
import com.dicoding.picodiploma.loginwithanimation.view.detail.DetailStory

class AdapterStory : RecyclerView.Adapter<AdapterStory.StoryViewHolder>() {
    private var listStory : List<Story>? = null

    fun setStoryList (listStory: List<Story>?) {
        this.listStory = listStory
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): StoryViewHolder {
        val viewStory = StoryItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return StoryViewHolder(viewStory)
    }

    override fun onBindViewHolder(holder: StoryViewHolder, position: Int) {
        holder.bind(listStory?.get(position)!!)
    }

    override fun getItemCount(): Int {
        return if (listStory == null)0
        else listStory?.size!!
    }

    class StoryViewHolder(private val binding: StoryItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dataStory : Story) {
            binding. apply {
                photo.load(dataStory.photoUrl)
                nameUser.text = dataStory.name

                root.setOnClickListener {
                    val intentStory = Intent(binding.root.context, DetailStory::class.java)
                    intentStory.putExtra("storyData", dataStory)
                    root.context.startActivity(intentStory)
                }
            }
        }
    }
}