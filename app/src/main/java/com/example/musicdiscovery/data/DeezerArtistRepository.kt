package com.example.musicdiscovery.data

import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerResponseList
import com.example.musicdiscovery.model.Track
import com.example.musicdiscovery.network.DeezerApiService

interface DeezerArtistRepository {
    suspend fun searchArtist(name: String): DeezerResponseList<Artist>
    suspend fun getArtistTracks(artistId: Int): DeezerResponseList<Track>
}

class NetworkDeezerArtistRepository(
    private val deezerApiService: DeezerApiService
): DeezerArtistRepository {
    override suspend fun searchArtist(name: String): DeezerResponseList<Artist> = deezerApiService.getArtists(name);
    override suspend fun getArtistTracks(artistId: Int): DeezerResponseList<Track> {
        val tracks = deezerApiService.getArtistTracks(artistId, 10, 0) // TODO
        tracks.data.forEach { track ->
            val trackDetails = deezerApiService.getTrackDetails(track.id)
            track.trackDetails = trackDetails
        }

        return tracks;
    }
}