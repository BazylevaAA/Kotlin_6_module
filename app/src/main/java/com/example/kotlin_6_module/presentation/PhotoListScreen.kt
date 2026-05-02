package com.example.kotlin_6_module.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.kotlin_6_module.domain.Photo

@Composable
fun PhotoListScreen(
    viewModel: PhotoListViewModel,
    onPhotoClick: (Photo) -> Unit
) {
    val state by viewModel.state.collectAsState()

    when (state) {
        is PhotoListState.Loading -> {
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
        }

        is PhotoListState.Error -> {
            val message = (state as PhotoListState.Error).message
            Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text(text = "Ошибка: $message")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.loadPhotos() }) {
                        Text("Повторить")
                    }
                }
            }
        }

        is PhotoListState.Success -> {
            val photos = (state as PhotoListState.Success).photos
            LazyVerticalGrid(
                columns = GridCells.Fixed(2),
                contentPadding = PaddingValues(8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.fillMaxSize()
            ) {
                items(photos) { photo ->
                    PhotoCard(photo = photo, onClick = { onPhotoClick(photo) })
                }
            }
        }
    }
}

@Composable
fun PhotoCard(photo: Photo, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column {
            AsyncImage(
                model = "https://picsum.photos/id/${photo.id}/200/200",
                contentDescription = photo.author,
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(150.dp)
            )
            Column(modifier = Modifier.padding(8.dp)) {
                Text(
                    text = photo.author,
                    style = MaterialTheme.typography.bodyMedium,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = "${photo.width} × ${photo.height}",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}