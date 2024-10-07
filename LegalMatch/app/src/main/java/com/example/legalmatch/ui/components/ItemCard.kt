package com.example.legalmatch.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun ItemCard(title: String, description: String, onClick: () -> Unit) {

    var showFullDescription by remember { mutableStateOf(false) }

    Card(
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(10.dp),
        colors = CardColors(
            containerColor = Color.White,
            contentColor = Color.Black,
            disabledContainerColor = Color.LightGray,
            disabledContentColor = Color.DarkGray

        )
    ) {

        Column(modifier = Modifier.padding(16.dp)) {

            Text(text = title, fontWeight = FontWeight.Bold)
            //Text(text = description)
            val maxChar = 150
            if (showFullDescription) { Text(text = description) }
            else {
                val truncatedDescription = if (description.length > maxChar) {
                    description.substring(0, maxChar).plus("...")
                } else {
                    description
                }
                Text(text = truncatedDescription)
            }

        }

        // Ícono para expandir o colapsar la descripción

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
                    modifier = Modifier.padding(0.dp)
                )
            }
        }
    }
}