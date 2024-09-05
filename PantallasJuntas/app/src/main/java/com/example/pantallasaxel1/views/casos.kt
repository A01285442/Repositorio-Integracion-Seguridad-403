package com.example.vistasproyecto.vistas

import androidx.annotation.FloatRange
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.layout.ModifierInfo
import androidx.compose.ui.text.font.FontVariation.weight
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.pantallasaxel1.ui.theme.BlueTEC

//Vistas Abogado

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Casos(navController: NavController, modifier: Modifier = Modifier) {
    val scrollBehavior = TopAppBarDefaults.pinnedScrollBehavior(rememberTopAppBarState())

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
                    IconButton(onClick = { /* do something */ }) {
                        Icon(
                            imageVector = Icons.Filled.Menu,
                            contentDescription = "Localized description"
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