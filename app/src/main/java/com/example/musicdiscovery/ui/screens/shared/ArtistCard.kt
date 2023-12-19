package com.example.musicdiscovery.ui.screens.shared

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ArtistCard(
    artistId: Long,
    artistName: String,
    artistPicture: String,
    modifier: Modifier = Modifier,
    onArtistClick: ((artistId: Long) -> Unit),

    artistFans: Int? = null,

    isFavorite: Boolean = false,
    favoriteArtist: (() -> Unit)? = null
) {
    CustomCard(
        picture = artistPicture,
        contentDescription = artistName,
        modifier = modifier,
        onClick = { onArtistClick(artistId) }
    ) {
        Column(modifier = modifier.padding(vertical = 8.dp)) {
            Text(
                text = artistName,
                style = MaterialTheme.typography.headlineSmall
            )
            if (artistFans != null)
                Text(
                    text = "Fans: $artistFans",
                    style = MaterialTheme.typography.labelLarge
                )
        }

        if (favoriteArtist != null)
            Row(
                horizontalArrangement = Arrangement.End,
                modifier = Modifier.fillMaxWidth()
            ) {
                FavoriteButton(
                    isFavorite = isFavorite,
                    onFavoriteClick = favoriteArtist,
                    modifier = Modifier.padding(8.dp)
                )
            }
    }
}