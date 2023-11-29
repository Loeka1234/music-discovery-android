package com.example.musicdiscovery.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: Int,
    val name: String,

    val picture: String,
    @SerialName(value = "picture_small")
    val pictureSmall: String,
    @SerialName(value = "picture_medium")
    val pictureMedium: String,
    @SerialName(value = "picture_big")
    val pictureBig: String,
    @SerialName(value = "picture_xl")
    val pictureXl: String
)