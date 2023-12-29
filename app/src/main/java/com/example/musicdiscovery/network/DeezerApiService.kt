package com.example.musicdiscovery.network

import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerResponseList
import com.example.musicdiscovery.model.Track
import com.example.musicdiscovery.model.TrackDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    /**
     * Search an artist by String
     */
    @GET("search/artist")
    suspend fun getArtists(@Query("q") artistName: String, @Query("limit") limit: Int, @Query("index") index: Int): DeezerResponseList<Artist>

    /**
     * Get tracks of an artist by artistId
     */
    @GET("artist/{artistId}/top")
    suspend fun getArtistTracks(@Path("artistId") artistId: Long, @Query("limit") limit: Int, @Query("index") index: Int): DeezerResponseList<Track>

    /**
     * Get details of a track by trackId
     */
    @GET("track/{trackId}")
    suspend fun getTrackDetails(@Path("trackId") trackId: Long): TrackDetails

    /**
     * Get details of an artist by artistId
     */
    @GET("artist/{artistId}")
    suspend fun getArtistDetails(@Path("artistId") artistId: Long): Artist
}