package com.example.kotlin_6_module.data

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    private val logging = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(logging)
        .build()

    val api: PicsumApi = Retrofit.Builder()
        .baseUrl("https://picsum.photos/")
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(PicsumApi::class.java)
}