package com.example.proyectobueno.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.proyectobueno.ui.theme.BlueTEC

//Vistas Abogado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Casos(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())
    var expanded by remember { mutableStateOf(false)}

    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),

        topBar = {
            CenterAlignedTopAppBar(
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = Color.White,
                    titleContentColor = BlueTEC,
                ),
                title = {
                    Text(
                        text = "Casos",
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                },
//                navigationIcon = {
//                    IconButton(onClick = { /* do something */ }) {
//                        Icon(
//                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
//                            contentDescription = "Localized description"
//                        )
//                    }
//                },
                actions = {
                    IconButton(onClick = { expanded = true }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
                        )
                    }
                    DropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        DropdownMenuItem(
                            text = { Text("Perfil") },
                            onClick = {
                                navController.navigate("perfil/{email}")
                                expanded = false

                            }
                        )
                        DropdownMenuItem(
                            text = { Text("Salir") },
                            onClick = {
                                navController.navigate("login")
                                expanded = false
                            }
                        )
                    }
                },
                scrollBehavior = scrollBehavior,
            )
        },
    ) { innerPadding ->
        ScrollContent(innerPadding, navController = navController)
    }
}

@Composable
fun ScrollContent(innerPadding: PaddingValues, navController: NavController) {
    Column(
        modifier = Modifier
            .padding(innerPadding)
            .fillMaxWidth()
            .verticalScroll(rememberScrollState()),

        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()
        CaseScrollCard(navController = navController)
        CaseScrollSpace()

    }
}

@Composable
fun CaseScrollCard(navController: NavController){
    ElevatedCard(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),

        modifier = Modifier
            .size(width = 350.dp, height = 200.dp)
            .padding(6.dp),
        onClick = {
            navController.navigate("DetallesDeCaso")
        }

    ) {
        Row(
            modifier = Modifier
                .padding(15.dp)
        ){
            Icon(
                modifier = Modifier
                    .size(80.dp),
                imageVector = Icons.Outlined.AccountCircle,
                contentDescription = "dnjsakndsajk",
                tint = Color.Black

            )
            Column(
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(
                    text = "María Bolaños Amargo",
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = "Esta es la descripción de un caso. Esta es la descripción de un caso. Esta es la descripción de un caso. Esta es la descripción de un caso.  "
                )
            }
        }

    }
}

@Composable
fun CaseScrollSpace(){
    Card(
        modifier = Modifier
            .height(15.dp)
            .fillMaxWidth(),
        colors = CardDefaults.cardColors(
            containerColor = Color.Transparent,
        ),
    ){}
}