package com.example.musicdiscovery.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteArtistDao {
    /**
     * Insert a favorite artist
     */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FavoriteArtistEntity)

    /**
     * Update a favorite artist
     */
    @Update
    suspend fun update(item: FavoriteArtistEntity)

    /**
     * Delete a favorite artist
     */
    @Delete
    suspend fun delete(item: FavoriteArtistEntity)

    /**
     * Get a favorite artist
     */
    @Query("SELECT * from favorite_artists WHERE id = :id")
    fun getFavoriteArtist(id: Long): Flow<FavoriteArtistEntity>

    /**
     * Get all favorite artists
     */
    @Query("SELECT * from favorite_artists ORDER BY name ASC")
    fun getAllFavoriteArtists(): Flow<List<FavoriteArtistEntity>>
}