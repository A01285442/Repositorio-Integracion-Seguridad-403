package com.example.legalmatch

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.annotation.RequiresApi
import androidx.compose.material3.Surface
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.example.app.navigation.AppNavGraph
import com.example.legalmatch.ui.screens.AsesoriaViewModel
import com.example.legalmatch.ui.screens.CasosViewModel
import com.example.legalmatch.ui.theme.LegalMatchTheme

class MainActivity : ComponentActivity() {
    @RequiresApi(Build.VERSION_CODES.O)
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val CasosViewModel by viewModels<CasosViewModel>()
        enableEdgeToEdge()


        setContent {
            LegalMatchTheme {
                Surface {
                    val navController = rememberNavController()
                    val asesoriasViewModel: AsesoriaViewModel = viewModel()
                    AppNavGraph(navController = navController, asesoriasViewModel)
                }
            }
        }


    }
}