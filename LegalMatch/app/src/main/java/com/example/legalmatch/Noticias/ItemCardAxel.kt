package com.example.legalmatch.Noticias

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardColors
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage

@Composable
fun ItemCardAxel(
    title: String,
    description: String,
    imageUrl: String?
) {

    var showFullDescription by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray

        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {
            imageUrl?.let {
                AsyncImage(
                    model = it,
                    contentDescription = "Imagen de la noticia",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(bottom = 8.dp)
                )
            }

            Text(text = title, style = MaterialTheme.typography.titleSmall)
            //Text(text = description)
            val maxChar = 200
            if (showFullDescription) { Text(text = description, style = MaterialTheme.typography.bodySmall) }
            else {
                val truncatedDescription = if (description.length > maxChar) {
                    description.substring(0, maxChar).plus("...")
                } else {
                    description
                }
                Text(text = truncatedDescription, style = MaterialTheme.typography.bodySmall)
            }

        }
        Row(
            modifier = Modifier.fillMaxWidth().padding(0.dp),
            horizontalArrangement = Arrangement.End
        ) {
            TextButton(
                onClick = { showFullDescription = !showFullDescription },
            ) {
                Text(
                    text = if (showFullDescription) "Ver menos" else "Ver más",
                    color = MaterialTheme.colorScheme.primary,
                    modifier = Modifier.padding(0.dp),
                    textAlign = TextAlign.Justify
                )
            }
        }



    }
}