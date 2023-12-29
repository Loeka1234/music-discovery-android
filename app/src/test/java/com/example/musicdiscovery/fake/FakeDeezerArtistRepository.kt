package com.example.musicdiscovery.fake

import com.example.musicdiscovery.data.DeezerArtistRepository
import com.example.musicdiscovery.model.Artist
import com.example.musicdiscovery.model.DeezerResponseList
import com.example.musicdiscovery.model.Track

class FakeDeezerArtistRepository(artistsCount: Int) : DeezerArtistRepository {
    private val artists = arrayOfNulls<Artist>(artistsCount).mapIndexed { index, _ ->
        Artist(index.toLong(), "Artist name", "Picture", 100)
    }

    override suspend fun searchArtist(
        name: String,
        limit: Int,
        index: Int
    ): DeezerResponseList<Artist> {
        return DeezerResponseList(
            artists.subList(index, artists.count()).take(limit),
            artists.count()
        )
    }

    override suspend fun getArtistTracks(
        artistId: Long,
        limit: Int,
        index: Int
    ): DeezerResponseList<Track> {
        TODO("Not yet implemented")
    }

    override suspend fun getArtistDetails(artistId: Long): Artist {
        TODO("Not yet implemented")
    }
}