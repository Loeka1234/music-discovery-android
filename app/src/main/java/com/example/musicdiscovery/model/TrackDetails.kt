package com.example.musicdiscovery.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDetails (
    @SerialName(value = "release_date")
    val releaseDate: String,
)