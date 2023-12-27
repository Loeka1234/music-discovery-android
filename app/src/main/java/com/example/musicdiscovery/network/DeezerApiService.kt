package com.example.musicdiscovery.network

import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerResponseList
import com.example.musicdiscovery.model.Track
import com.example.musicdiscovery.model.TrackDetails
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DeezerApiService {
    @GET("search/artist")
    suspend fun getArtists(@Query("q") artistName: String, @Query("limit") limit: Int, @Query("index") index: Int): DeezerResponseList<Artist>
    @GET("artist/{artistId}/top")
    suspend fun getArtistTracks(@Path("artistId") artistId: Long, @Query("limit") limit: Int, @Query("index") index: Int): DeezerResponseList<Track>
    @GET("track/{trackId}")
    suspend fun getTrackDetails(@Path("trackId") trackId: Long): TrackDetails
    @GET("artist/{artistId}")
    suspend fun getArtistDetails(@Path("artistId") artistId: Long): Artist
}