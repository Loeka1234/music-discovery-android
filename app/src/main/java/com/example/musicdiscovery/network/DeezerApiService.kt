package com.example.musicdiscovery.network

import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerArtistSearchData
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerApiService {
    @GET("search/artist")
    suspend fun getArtists(@Query("q") artistName: String): DeezerArtistSearchData
}