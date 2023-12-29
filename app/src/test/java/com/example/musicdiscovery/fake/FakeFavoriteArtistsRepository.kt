package com.example.musicdiscovery.fake

import com.example.musicdiscovery.data.Event
import com.example.musicdiscovery.data.FavoriteArtistsRepository
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class FakeFavoriteArtistsRepository: FavoriteArtistsRepository {
    private val favoriteArtists: ArrayList<FavoriteArtistEntity> = arrayListOf()
    override fun getAllFavoriteArtistsStream(): Flow<List<FavoriteArtistEntity>> {
        return flowOf(favoriteArtists)
    }

    override fun getFavoriteArtistStream(id: Long): Flow<FavoriteArtistEntity?> {
        TODO("Not yet implemented")
    }

    override suspend fun insertFavoriteArtist(favoriteArtist: FavoriteArtistEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun deleteFavoriteArtist(favoriteArtist: FavoriteArtistEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun updateFavoriteArtist(favoriteArtist: FavoriteArtistEntity) {
        TODO("Not yet implemented")
    }

    override suspend fun insertOrDeleteFavoriteArtistIfExists(favoriteArtist: FavoriteArtistEntity): Event {
        return if (favoriteArtists.contains(favoriteArtist)) {
            favoriteArtists.remove(favoriteArtist)
            Event.DELETED
        } else {
            favoriteArtists.add(favoriteArtist)
            Event.INSERTED
        }

    }
}