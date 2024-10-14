package com.example.legalmatch.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight

@Composable
fun SpacedInformation(
    label: String,
    value: String,
    style: TextStyle = MaterialTheme.typography.bodyMedium
){
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.Absolute.SpaceBetween
    ){
        Text(label, style = style)
        Text(value, style = style)
    }
}


@Composable
fun ProfileStat(big: String, medium: String, description: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row {
            Text(text = big, style = MaterialTheme.typography.headlineMedium, fontWeight = FontWeight.Bold)
            Text(text = medium, style = MaterialTheme.typography.headlineSmall, fontWeight = FontWeight.Bold)
        }

        Text(text = description, style = MaterialTheme.typography.bodySmall, color = Color.Gray)
    }
}