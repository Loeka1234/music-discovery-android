package com.example.musicdiscovery.model

import kotlinx.serialization.Serializable

@Serializable
data class DeezerResponseList<T> (
    val data: List<T>
)