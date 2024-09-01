package com.example.vistadeperfil

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.pantallasaxel1.ui.theme.BlueTEC


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Profile") },
                navigationIcon = {
                    IconButton(onClick = { /* Handle back action */ }) {
                        /*Icon(
                            painter = painterResource(id = R.drawable.ic_back_arrow),
                            contentDescription = "Back"
                        )*/
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                /*
Image(
    painter = painterResource(id = R.drawable.ic_profile_placeholder),
    contentDescription = "Profile Picture",
    modifier = Modifier
        .size(100.dp)
        .clip(CircleShape)
)
*/
                Box(
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .background(Color.Gray), // Puedes usar un color de fondo en lugar de una imagen
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "No Image", color = Color.White) // Texto temporal
                }

                Spacer(modifier = Modifier.height(10.dp))

                // Nombre y correo electrónico
                Text(
                    text = "Donald J. Trump",
                    fontSize = 22.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "dTrump@gmail.com",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Descripción
                Text(
                    text = "Description",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "I am a great lawyer, you should probably look for me...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(20.dp))

                // Información adicional (usando Card de Material Design)
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Row(
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "2 años", fontWeight = FontWeight.Bold)
                                Text(text = "Carrera", color = Color.Gray)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "4.7★", fontWeight = FontWeight.Bold)
                                Text(text = "252 Reviews", color = Color.Gray)
                            }

                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(text = "Violencia", fontWeight = FontWeight.Bold)
                                Text(text = "Categoria Favorita", color = Color.Gray)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(20.dp))

                // Información adicional
                Text(
                    text = "Informacion adicional",
                    fontWeight = FontWeight.Bold,
                    modifier = Modifier.fillMaxWidth()
                )
                Text(
                    text = "Despues de estudiar por 5 años puedo decir que son un excelente abogado...",
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Justify
                )

                Spacer(modifier = Modifier.height(30.dp))

                // Botón de guardar información
                Button(
                    onClick = { /* Handle save action */ },
                    modifier = Modifier.fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueTEC,
                        contentColor = Color.White
                    ),
                ) {
                    Text(text = "Guarda información")
                }
            }
        }
    }
}