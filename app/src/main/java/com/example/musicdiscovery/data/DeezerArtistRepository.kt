package com.example.musicdiscovery.data

import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerArtistSearchData
import com.example.musicdiscovery.network.DeezerApiService

interface DeezerArtistRepository {
    suspend fun searchArtist(name: String): DeezerArtistSearchData
}

class NetworkDeezerArtistRepository(
    private val deezerApiService: DeezerApiService
): DeezerArtistRepository {
    override suspend fun searchArtist(name: String): DeezerArtistSearchData = deezerApiService.getArtists(name);
}