package com.idzcasey.wavesofsilence.composables

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LibraryMusic
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.idzcasey.wavesofsilence.data.LibraryCategoryData

@Composable
fun LibraryCategory(data: LibraryCategoryData, modifier: Modifier = Modifier) {
    Row(modifier
        .padding(8.dp)
        .fillMaxWidth()
        .clickable(enabled = true, onClick = data.onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp)) {
        Icon(data.icon, contentDescription = data.name)
        Text(data.name, style = MaterialTheme.typography.titleLarge)
    }
}


@Preview
@Composable
fun LibraryCategoryPreview() {

    LibraryCategory(
        LibraryCategoryData(
            name = "All Sounds",
            onClick = {},
            icon = Icons.Filled.LibraryMusic
        )
    )
}