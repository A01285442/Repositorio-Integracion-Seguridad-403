package com.example.legalmatch

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.example.app.navigation.AppNavGraph
import com.example.legalmatch.ui.screens.CasosScreen
import com.example.legalmatch.ui.screens.CasosViewModel
import com.example.legalmatch.ui.theme.LegalMatchTheme

class MainActivity : ComponentActivity() {
    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val CasosViewModel by viewModels<CasosViewModel>()
        enableEdgeToEdge()


        setContent {
            LegalMatchTheme {
                Surface {
                    val navController = rememberNavController()
                    AppNavGraph(navController = navController)
                }
            }
        }


    }
}