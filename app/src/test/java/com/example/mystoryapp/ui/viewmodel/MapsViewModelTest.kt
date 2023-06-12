package com.example.mystoryapp.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.MutableLiveData
import com.example.mystoryapp.data.Outcome
import com.example.mystoryapp.data.StoriesResponse
import com.example.mystoryapp.data.StoryRepo
import com.example.mystoryapp.utils.DataDummy
import com.example.mystoryapp.utils.getOrAwaitValue
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
internal class MapsViewModelTest {
    @get:Rule
    val instantExecutorRule = InstantTaskExecutorRule()

    @Mock
    private lateinit var storyRepos: StoryRepo

    private lateinit var mapsViewModel: MapsViewModel
    private val dummyStoriesResponse = DataDummy.generateDummyStories()

    @Before
    fun setUp() {
        mapsViewModel = MapsViewModel(storyRepos)
    }

    @Test
    fun `when getStoriesWithLocation Should Not Null and return success`() {
        val expectedStoryResponse = MutableLiveData<Outcome<StoriesResponse>>()
        expectedStoryResponse.value = Outcome.Success(dummyStoriesResponse)

        Mockito.`when`(storyRepos.getStoriesWithLocation()).thenReturn(expectedStoryResponse)

        val actualStories = mapsViewModel.getStoriesWithLocation().getOrAwaitValue()
        Mockito.verify(storyRepos).getStoriesWithLocation()
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Outcome.Success)
        Assert.assertEquals(dummyStoriesResponse.listStory.size, (actualStories as Outcome.Success).data.listStory.size)
    }

    @Test
    fun `when Network Error Should Return Error`() {
        val expectedStoryResponse = MutableLiveData<Outcome<StoriesResponse>>()
        expectedStoryResponse.value = Outcome.Error("network error")

        Mockito.`when`(storyRepos.getStoriesWithLocation()).thenReturn(expectedStoryResponse)

        val actualStories = mapsViewModel.getStoriesWithLocation().getOrAwaitValue()
        Mockito.verify(storyRepos).getStoriesWithLocation()
        Assert.assertNotNull(actualStories)
        Assert.assertTrue(actualStories is Outcome.Error)
    }
}