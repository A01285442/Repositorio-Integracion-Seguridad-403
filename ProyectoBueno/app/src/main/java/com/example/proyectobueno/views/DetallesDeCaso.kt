package com.example.proyectobueno.views


import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.proyectobueno.assets.MyTopAppBar
import com.example.proyectobueno.ui.theme.BlueTEC


@Composable
fun DetallesDeCaso(navController: NavController, modifier: Modifier = Modifier) {
    Scaffold(
        topBar = {
            MyTopAppBar(
                title = "Detalles",
                navigationIcon = Icons.Default.Clear,
                onNavigationClick = {navController.popBackStack()}
            )
        },
        content = { innerPadding ->
            Column(modifier = Modifier
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
            ) {
                Card(
                    onClick = { /*TODO*/ },

                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    Column(modifier = Modifier.padding(12.dp)) {
                        Icon(
                            imageVector = Icons.Default.AccountCircle,
                            contentDescription = "Navigation Icon",
                            modifier = Modifier
                                .fillMaxWidth()
                                .wrapContentSize(Alignment.Center)
                                .size(100.dp)
                        )
                        Text(
                            text = "Nombre de la persona",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 24.sp
                        )
                        Text(
                            text = "Correo de la persona",
                            textAlign = TextAlign.Center,
                            modifier = Modifier.fillMaxWidth(),
                            fontSize = 18.sp
                        )
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Descripción",
                            fontSize = 18.sp
                        )
                        Text(text = "El cliente está buscando representación legal para un caso de"+
                                "lesiones personales relacionado con un accidente automovilístico"+
                                "que ocurrió el 15 de enero de 2023. El cliente sufrió múltiples"+
                                "lesiones y está buscando una compensación por los gastos médicos"+
                                "y los salarios perdidos.")
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Información Adicional",
                            fontSize = 18.sp)
                        Text(text = "El cliente ya ha recopilado registros médicos e informes "+
                                "policiales relacionados con el accidente. Está buscando un "+
                                "abogado que se especialice en casos de lesiones personales y "+
                                "tenga un sólido historial de ganar acuerdos. El cliente ya ha "+
                                "recopilado registros médicos e informes policiales relacionados "+
                                "con el accidente. Está buscando un abogado que se especialice en "+
                                "casos de lesiones personales y tenga un sólido historial de ganar "+
                                "acuerdos.")
                        Spacer(modifier = Modifier.height(20.dp))
                        Text(
                            text = "Información del Cliente",
                            fontSize = 18.sp
                        )
                        Text("Full name: Esteban Sierra Baccio \n" +
                                "Género: Masculino \n" +
                                "Nacimiento: 16 de septiembre del 2003 \n" +
                                "Número celular: 54 5543 6675")
                    }
                }
                Spacer(modifier = Modifier.height(20.dp))
                Button(
                    onClick = {
                        navController.navigate("chatAbogado")
                    },
                    colors = ButtonDefaults.buttonColors(
                        containerColor = BlueTEC,
                        contentColor = Color.White
                    ),
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    Text("Contactar")
                }
            }


        }
    )
}

