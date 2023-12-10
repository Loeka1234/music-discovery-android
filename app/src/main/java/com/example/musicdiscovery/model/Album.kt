package com.example.musicdiscovery.model

import kotlinx.serialization.Serializable

@Serializable
data class Album (
    val id: Long,
    val cover: String
)