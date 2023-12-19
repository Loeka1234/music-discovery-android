package com.example.musicdiscovery.data

import com.example.musicdiscovery.entity.FavoriteArtistEntity
import kotlinx.coroutines.flow.Flow

enum class Event {
    INSERTED, DELETED
}

interface FavoriteArtistsRepository {
    fun getAllFavoriteArtistsStream(): Flow<List<FavoriteArtistEntity>>

    fun getFavoriteArtistStream(id: Long): Flow<FavoriteArtistEntity?>

    suspend fun insertFavoriteArtist(favoriteArtist: FavoriteArtistEntity)

    suspend fun deleteFavoriteArtist(favoriteArtist: FavoriteArtistEntity)

    suspend fun updateFavoriteArtist(favoriteArtist: FavoriteArtistEntity)

    suspend fun insertOrDeleteFavoriteArtistIfExists(favoriteArtist: FavoriteArtistEntity): Event
}