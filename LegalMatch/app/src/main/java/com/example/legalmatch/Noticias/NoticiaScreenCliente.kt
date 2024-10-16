package com.example.legalmatch.Noticias

import android.net.Uri
import android.os.Build
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.legalmatch.ui.components.CustomBottomBarClientes
import com.example.legalmatch.ui.components.CustomTopBar


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoticiasScreenCliente(navController: NavHostController, noticiasViewModel: NoticiasViewModel) {
    val noticiasState = noticiasViewModel.noticiasState.collectAsState().value
    var selectedImageUri by remember { mutableStateOf<Uri?>(null) }
    val isLoading by noticiasViewModel.isLoading.collectAsState()
    val errorMessage = remember { mutableStateOf<String?>(null) }

    val getImage = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        selectedImageUri = uri
    }



    Scaffold(
        topBar = { CustomTopBar(title = "Noticias", navIcon = false, actIcon = false) },
        bottomBar = { CustomBottomBarClientes(navController = navController) },
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding),
            contentAlignment = Alignment.Center
        ) {
            if(isLoading){
                CircularProgressIndicator()
            }
            else{
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ){
                    items(noticiasState){ noticia ->
                        NoticiaItemCliente(navController = navController, noticia = noticia, noticiasViewModel = noticiasViewModel)
                    }
                }
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun NoticiaItemCliente(navController: NavHostController, noticia: Noticia,noticiasViewModel: NoticiasViewModel) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        ItemCardAxel(
            title = noticia.titulo,
            description = noticia.descripcion,
            imageUrl = noticia.imagenurl
        )
    }
}



