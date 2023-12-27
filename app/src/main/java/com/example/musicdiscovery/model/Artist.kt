package com.example.musicdiscovery.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Artist(
    val id: Long,
    val name: String,

    val picture: String,

    @SerialName(value = "nb_fan")
    val nbFan: Int
)