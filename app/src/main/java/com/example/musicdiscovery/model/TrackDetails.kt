package com.example.musicdiscovery.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class TrackDetails (
    val bpm: Float,
    @SerialName(value = "release_date")
    val releaseDate: String,
    val gain: Float
)