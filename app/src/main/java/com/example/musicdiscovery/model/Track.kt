package com.example.musicdiscovery.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient

@Serializable
data class Track (
    val id: Long,
    val title: String,
    val duration: Int,
    val album: Album,

    @Transient
    var trackDetails: TrackDetails? = null
)