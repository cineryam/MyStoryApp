package com.example.mystoryapp.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.paging.filter
import coil.load
import com.example.mystoryapp.data.Story
import com.example.mystoryapp.databinding.ActivityStoryDetailBinding
import com.example.mystoryapp.ui.StoriesAdapter
import com.example.mystoryapp.ui.viewmodel.StoriesViewModel
import com.example.mystoryapp.utils.ViewModelFactory

class StoryDetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityStoryDetailBinding
    private lateinit var storiesAdapter: StoriesAdapter

    private val storiesViewModel: StoriesViewModel by viewModels {
        ViewModelFactory(applicationContext)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityStoryDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        storiesAdapter = StoriesAdapter()

        storiesViewModel.getStories.observe(this, { pagingData ->
            storiesAdapter.submitData(lifecycle, pagingData.filter { it.name.isNotEmpty() && it.photoUrl.isNotEmpty() && it.description.isNotEmpty() })
        })

        val detailStory = Story(
            createdAt = "",
            description = intent.getStringExtra("description") ?: "",
            id = intent.getStringExtra("id") ?: "",
            lat = intent.getDoubleExtra("lat", 0.0),
            lon = intent.getDoubleExtra("lon", 0.0),
            name = intent.getStringExtra("name") ?: "",
            photoUrl = intent.getStringExtra("photo_url") ?: ""
        )

        binding.ivDetailPhoto.load(detailStory.photoUrl)
        binding.tvDetailName.text = detailStory.name
        binding.tvDetailDescription.text = detailStory.description
    }
}