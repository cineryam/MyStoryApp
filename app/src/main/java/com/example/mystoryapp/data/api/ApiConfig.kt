package com.example.mystoryapp.data.api

import android.content.Context
import com.example.mystoryapp.BuildConfig
import com.example.mystoryapp.data.AuthInterceptor
import com.example.mystoryapp.utils.Preference
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiConfig {
    companion object{
        private fun getInterceptor(token: String?, isDebug: Boolean): OkHttpClient {
            val loggingInterceptor = HttpLoggingInterceptor().apply {
                level = if (isDebug) HttpLoggingInterceptor.Level.BODY else HttpLoggingInterceptor.Level.NONE
            }

            return if (token.isNullOrEmpty()) {
                OkHttpClient.Builder()
                    .addInterceptor(loggingInterceptor)
                    .build()
            } else {
                OkHttpClient.Builder()
                    .addInterceptor(AuthInterceptor(token))
                    .addInterceptor(loggingInterceptor)
                    .build()
            }
        }

        fun getApiService(context: Context): ApiService {
            val sharedPref = Preference.initPref(context, "onSignIn")
            val token = sharedPref.getString("token", null).toString()

            val isDebug = BuildConfig.DEBUG

            val retrofit = Retrofit.Builder()
                .baseUrl("https://story-api.dicoding.dev/v1/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getInterceptor(token, isDebug))
                .build()
            return retrofit.create(ApiService::class.java)
        }
    }
}