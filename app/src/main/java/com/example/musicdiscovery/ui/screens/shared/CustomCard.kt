package com.example.musicdiscovery.ui.screens.shared

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomCard(
    modifier: Modifier = Modifier,
    picture: String,
    contentDescription: String,
    onClick: () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Card(modifier = modifier.fillMaxWidth(), onClick = onClick) {
        Row (verticalAlignment = Alignment.CenterVertically) {
            AsyncImage (
                model = picture,
                contentDescription = contentDescription,
                modifier = modifier
                    .height(80.dp)
                    .width(80.dp)
                    .padding(8.dp)
                    .clip(RoundedCornerShape(0.dp, 8.dp, 0.dp, 8.dp)),
                contentScale = ContentScale.Crop,
            )
            content()
        }
    }
}