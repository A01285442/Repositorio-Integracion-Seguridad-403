package com.example.pantallasaxel1

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.clickable
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.draw.clip
import androidx.compose.foundation.shape.RoundedCornerShape
import com.example.pantallasaxel1.ui.theme.BlueTEC
import com.example.pantallasaxel1.ui.theme.GrayTEC

@Composable
fun Categories() {
    val categories = listOf(
        "Violencia",
        "Disputas propiedad",
        "Derecho familiar",
        "Derechos civiles",
        "Derecho laboral",
        "Lesiones"
    )

    Column(
        modifier = Modifier.padding(16.dp)
    ) {
        Text(
            text = "Categorías",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = BlueTEC,
            modifier = Modifier.padding(bottom = 60.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier.weight(1f)
        ) {
            items(categories) { category ->
                CategoryItem(category, onClick = {

                })
            }
        }

        Button(
            onClick = {
                // boton "+"
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = BlueTEC,
                contentColor = Color.White
            ),
            modifier = Modifier
                .padding(top = 16.dp)
                .align(Alignment.CenterHorizontally)
                .width(100.dp)
                .height(100.dp)
        ) {
            Text(
                text = "+",
                fontSize = 36.sp,
                fontWeight = FontWeight.Bold
            )
        }

    }
}

@Composable
fun CategoryItem(category: String, onClick: () -> Unit) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = Modifier
            .padding(8.dp)
            .fillMaxWidth()
            .height(120.dp)  // Tamaño del recuadro
            .clip(RoundedCornerShape(16.dp))
            .background(Color.White)
            .border(width = 2.dp, color = GrayTEC, shape = RoundedCornerShape(16.dp))
            .clickable(onClick = onClick) // Hacer clic en el recuadro
    ) {
        Text(
            text = category,
            fontSize = 18.sp,
            color = BlueTEC,
            fontWeight = FontWeight.Medium,
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
