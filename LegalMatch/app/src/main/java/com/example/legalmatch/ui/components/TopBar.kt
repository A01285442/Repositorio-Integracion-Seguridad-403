package com.example.legalmatch.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.legalmatch.ui.theme.AzulTec
import com.example.legalmatch.viewmodel.SearchViewModel

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CustomTopBar(
    title: String,
    navIcon: Boolean,
    actIcon: Boolean,
    navController: NavController = rememberNavController(),
    rutaActButton: String = "",
    searchButton: Boolean = false,
    searchVM: SearchViewModel = SearchViewModel()
) {
    var searchStarted by remember{ mutableStateOf(false)}


    CenterAlignedTopAppBar(
        modifier = Modifier.shadow(8.dp).height(90.dp),
        colors = TopAppBarColors(
            containerColor = Color.White,
            scrolledContainerColor = Color.Black,
            navigationIconContentColor = Color.Black,
            titleContentColor = AzulTec,
            actionIconContentColor = Color.Black
        ),
        title = {
            if(searchStarted){
                BasicTextField(
                    value = searchVM.searchText,
                    onValueChange = { searchVM.updateSearchText(it)},
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(0.dp) // Sin padding externo
                        .border(1.dp, Color.Gray, RoundedCornerShape(4.dp))
                        .height(40.dp),
                    textStyle = TextStyle(fontSize = 18.sp), // Tamaño del texto
                    decorationBox = { innerTextField ->
                        // Caja sin padding
                        Box(
                            Modifier
                                .padding(0.dp) // Elimina padding interno
                                .background(Color.White) // Fondo
                                .padding(8.dp) // Ajusta el padding alrededor del texto
                        ) {
                            innerTextField() // Renderiza el texto
                        }
                    }
                )
            }
            else Text(text = title, fontWeight = FontWeight.Bold)
        },

        navigationIcon = {
            if(navIcon){
                IconButton(onClick = { navController.popBackStack() }) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Localized description"
                    )
                }
            }

        },
        actions = {
            if(searchButton){
                IconButton(onClick = {searchStarted = true}) {
                    Icon(imageVector =  Icons.Default.Search, contentDescription = "Localized description")
                }
            }

            if(actIcon){
                IconButton(onClick = { navController.navigate(route = rutaActButton) }) {
                    Icon(
                        imageVector = Icons.Filled.Add,
                        contentDescription = "Localized description"
                    )
                }
            }

        },

    )
}
