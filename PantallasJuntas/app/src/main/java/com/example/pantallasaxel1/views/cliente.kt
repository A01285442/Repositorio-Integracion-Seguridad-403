@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pantallasaxel1.views

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.pantallasaxel1.R
import com.example.pantallasaxel1.ui.theme.ClienteViewsTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientView(navController: NavHostController) {
    ClienteViewsTheme {
        val navController = rememberNavController()

        Scaffold(
            modifier = Modifier.fillMaxSize(),
            topBar = {
                TopAppBar(
                    title = {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Image(
                                painter = painterResource(id = R.drawable.logomartillo),
                                contentDescription = null,
                                modifier = Modifier.size(55.dp)
                            )
                            Text(
                                text = "Legal-Match",
                                color = Color.White,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = { navController.navigate("add") }) {
                            Icon(
                                imageVector = Icons.Filled.Add,
                                tint = Color.White,
                                contentDescription = "Add"
                            )
                        }
                        IconButton(onClick = { /* TODO: Search action */ }) {
                            Icon(
                                imageVector = Icons.Filled.Search,
                                tint = Color.White,
                                contentDescription = "Search",
                            )
                        }
                        IconButton(onClick = { /* TODO: Settings action */ }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                tint = Color.White,
                                contentDescription = "Settings"
                            )
                        }
                    },
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "home",
                modifier = Modifier.padding(innerPadding)
            ) {
                composable("home") { ContentWithBoxes() }
                composable("add") { MessagingView(navController = navController) }
            }
        }
    }
}

@Composable
fun ContentWithBoxes(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        ContentBox(
            title = "Divorcio",
            date = "21/07/2023",
            description = "Después de años de conflictos, mi pareja y yo decidimos divorciarnos. Presentamos los papeles y comenzamos el proceso legal para la separación de bienes y la custodia de nuestros hijos."
        )
        ContentBox(
            title = "Violencia Familiar",
            date = "17/04/2024",
            description = "Mi pareja me insulta y me amenaza cada vez que se enfada. Sus ataques verbales y físicos me dejan aterrorizada, sin saber cuándo o cómo se desatará la próxima agresión."
        )
        ContentBox(
            title = "Robo de bicicleta",
            date = "02/02/2024",
            description = "Dejé mi bicicleta asegurada en el parque y, al volver, ya no estaba. La bicicleta fue robada y no hay señales de su paradero. Tengo sospechas del culpable, pero no tengo pruebas."
        )
    }
}

@Composable
fun ContentBox(title: String, date: String, description: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp),

        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color.Black
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = description,
                fontSize = 14.sp,
                color = Color.Gray
            )
        }
    }
}

@Composable
fun MessagingView(navController: NavHostController) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Pair<String, Color>>() }

    // Automatically send a message from someone else when the view opens
    LaunchedEffect(Unit) {
        messages.add("Buenas tardes, soy el sistema automatizado de registro de Casos de Legal-Match. \n Cuéntame tu caso" to Color(android.graphics.Color.parseColor("#495D92")))
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        // Back Button
        IconButton(onClick = { navController.navigateUp() }) {
            Icon(imageVector = Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Messages list
        LazyColumn(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth(),
            reverseLayout = true
        ) {
            items(messages.size) { index ->
                val (message, color) = messages[index]
                MessageItem(message = message, color = color)
            }
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Input field and send button
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = messageText,
                onValueChange = { messageText = it },
                modifier = Modifier.weight(1f),
                placeholder = { Text("Type a message...") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (messageText.isNotBlank()) {
                    messages.add(0, messageText to Color.Gray)
                    messageText = ""
                }
            }) {
                Text("Send")
            }
        }
    }
}

@Composable
fun MessageItem(message: String, color: Color) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(4.dp)
            .background(color, RoundedCornerShape(8.dp))
            .padding(8.dp)
    ) {
        Text(text = message, color = Color.White)
    }
}
