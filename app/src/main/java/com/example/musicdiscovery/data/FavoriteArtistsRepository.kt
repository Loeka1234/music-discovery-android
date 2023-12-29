package com.example.musicdiscovery.data

import com.example.musicdiscovery.entity.FavoriteArtistEntity
import kotlinx.coroutines.flow.Flow

enum class Event {
    INSERTED, DELETED
}

interface FavoriteArtistsRepository {
    /**
     * Get all favorite artists
     */
    fun getAllFavoriteArtistsStream(): Flow<List<FavoriteArtistEntity>>

    /**
     * Get a favorite artist by id
     */

    fun getFavoriteArtistStream(id: Long): Flow<FavoriteArtistEntity?>

    /**
     * Insert a favorite artist
     */

    suspend fun insertFavoriteArtist(favoriteArtist: FavoriteArtistEntity)

    /**
     * Delete a favorite artist
     */

    suspend fun deleteFavoriteArtist(favoriteArtist: FavoriteArtistEntity)

    /**
     * Update a favorite artist
     */

    suspend fun updateFavoriteArtist(favoriteArtist: FavoriteArtistEntity)

    /**
     * Insert a favorite artist if not exists, deletes a favorite artist if exists
     */

    suspend fun insertOrDeleteFavoriteArtistIfExists(favoriteArtist: FavoriteArtistEntity): Event
}