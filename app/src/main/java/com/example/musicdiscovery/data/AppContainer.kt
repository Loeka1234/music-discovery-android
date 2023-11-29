package com.example.musicdiscovery.data

import com.example.musicdiscovery.network.DeezerApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import retrofit2.Retrofit
import okhttp3.MediaType.Companion.toMediaType

interface AppContainer {
    val deezerArtistRepository: DeezerArtistRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "https://api.deezer.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .build()

    private val retroFitService: DeezerApiService by lazy {
        retrofit.create(DeezerApiService::class.java)
    }

    override val deezerArtistRepository: DeezerArtistRepository by lazy {
        NetworkDeezerArtistRepository(retroFitService)
    }
}