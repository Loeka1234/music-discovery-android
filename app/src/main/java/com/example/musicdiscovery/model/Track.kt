package com.example.musicdiscovery.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.Transient
import java.util.Date

@Serializable
data class Track (
    val id: Long,
    val title: String,
    val duration: Int,
    val album: Album,

    @Transient
    var trackDetails: TrackDetails? = null
)