package com.example.mystoryapp.utils

import com.example.mystoryapp.data.*

object DataDummy {
    fun generateDummyStories(): StoriesResponse {
        val listStory = ArrayList<Story>()
        for (i in 1..20) {
            val story = Story(
                createdAt = "2023-05-05T05:05:05Z",
                description = "Description $i",
                id = "id_$i",
                lat = i.toDouble() * 10,
                lon = i.toDouble() * 10,
                name = "Name $i",
                photoUrl = "https://static.wikia.nocookie.net/kboy-group/images/1/15/NCT_DREAM_%28Candy%29_Group_%284%29.jpg/revision/latest/scale-to-width-down/350?cb=20221210223452"
            )
            listStory.add(story)
        }

        return StoriesResponse(
            error = false,
            message = "Stories fetched success",
            listStory = listStory
        )
    }

    fun generateDummyAddStory(): PostStoryResponse {
        return PostStoryResponse(
            error = false,
            message = "success"
        )
    }

    fun generateDummyRegister(): RegisterResponse {
        return RegisterResponse(
            error = false,
            message = "User Created"
        )
    }

    fun generateDummyLogin(): LoginResponse {
        return LoginResponse(
            error = false,
            message = "success",
            loginResult = LoginResult(
                userId = "user-cz3ab_WINS_YoS13",
                name = "Cindy Ratna Amalia",
                token = "jdksjnGbJhsjwkWowFANtasTiCbAbyDANCe.uuuIwokjNjnsnsnjhjopQQ.knJJencijhjheviwibiANGkwiT__j98HjnbyUy"
            )
        )
    }
}