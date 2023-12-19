package com.example.musicdiscovery.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorite_artists")
data class FavoriteArtistEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Long,
    val name: String,
    val picture: String,
)