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
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(item: FavoriteArtistEntity)

    @Update
    suspend fun update(item: FavoriteArtistEntity)

    @Delete
    suspend fun delete(item: FavoriteArtistEntity)

    @Query("SELECT * from favorite_artists WHERE id = :id")
    fun getFavoriteArtist(id: Long): Flow<FavoriteArtistEntity>

    @Query("SELECT * from favorite_artists ORDER BY name ASC")
    fun getAllFavoriteArtists(): Flow<List<FavoriteArtistEntity>>
}