package com.example.musicdiscovery.data

import android.content.Context
import android.util.Log
import com.example.musicdiscovery.network.DeezerApiService
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.IOException

interface AppContainer {
    val deezerArtistRepository: DeezerArtistRepository
    val favoriteArtistsRepository: FavoriteArtistsRepository
}

class DefaultAppContainer(private val context: Context) : AppContainer {
    private val baseUrl = "https://api.deezer.com/"

    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(Json {
            ignoreUnknownKeys = true
        }.asConverterFactory("application/json".toMediaType()))
        .baseUrl(baseUrl)
        .client(OkHttpClient
            .Builder()
            .addInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
            .build())
        .build()

    private val retroFitService: DeezerApiService by lazy {
        retrofit.create(DeezerApiService::class.java)
    }

    override val deezerArtistRepository: DeezerArtistRepository by lazy {
        NetworkDeezerArtistRepository(retroFitService)
    }

    override val favoriteArtistsRepository: FavoriteArtistsRepository by lazy {
        OfflineFavoriteArtistsRepository(MusicDiscoveryDatabase.getDatabase(context).favoriteArtistDao())
    }
}