package com.example.musicdiscovery.data

import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerResponseList
import com.example.musicdiscovery.model.Track
import com.example.musicdiscovery.network.DeezerApiService

interface DeezerArtistRepository {
    /**
     * Search artists by string
     */
    suspend fun searchArtist(name: String, limit: Int, index: Int): DeezerResponseList<Artist>

    /**
     * Get the tracks of an artist with given id
     */
    suspend fun getArtistTracks(artistId: Long, limit: Int, index: Int): DeezerResponseList<Track>

    /**
     * Get the details of an artist with given id
     */
    suspend fun getArtistDetails(artistId: Long): Artist
}

class NetworkDeezerArtistRepository(
    private val deezerApiService: DeezerApiService
): DeezerArtistRepository {
    override suspend fun searchArtist(name: String, limit: Int, index: Int): DeezerResponseList<Artist> = deezerApiService.getArtists(name, limit, index)
    override suspend fun getArtistTracks(artistId: Long, limit: Int, index: Int): DeezerResponseList<Track> {
        val tracks = deezerApiService.getArtistTracks(artistId, limit, index)
        tracks.data.forEach { track ->
            val trackDetails = deezerApiService.getTrackDetails(track.id)
            track.trackDetails = trackDetails
        }

        return tracks
    }
    override suspend fun getArtistDetails(artistId: Long): Artist = deezerApiService.getArtistDetails(artistId)
}