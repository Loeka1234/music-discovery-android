package com.example.musicdiscovery.data

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.musicdiscovery.dao.FavoriteArtistDao
import com.example.musicdiscovery.entity.FavoriteArtistEntity
import com.example.musicdiscovery.model.Artist
import android.content.Context
import androidx.room.Room

@Database(entities = [FavoriteArtistEntity::class], version = 1, exportSchema = false)
abstract class MusicDiscoveryDatabase: RoomDatabase() {
    abstract fun favoriteArtistDao(): FavoriteArtistDao

    companion object {
        @Volatile
        private var Instance: MusicDiscoveryDatabase? = null

        fun getDatabase(context: Context): MusicDiscoveryDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, MusicDiscoveryDatabase::class.java, "music_discovery_database")
                    .build().also { Instance = it }
            }
        }
    }
}