package com.example.musicdiscovery.model

import kotlinx.serialization.Serializable

@Serializable
data class DeezerArtistSearchData (
    val data: List<Artist>
)