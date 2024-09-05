@file:OptIn(ExperimentalMaterial3Api::class)

package com.example.pantallasaxel1.views

import android.annotation.SuppressLint
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

//.json
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.pantallasaxel1.models.caseCliente
import com.example.pantallasaxel1.ui.theme.BlueTEC
import com.example.pantallasaxel1.ui.theme.WhiteTEC

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.time.LocalDateTime

//cosas del calendario
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.ui.text.style.TextOverflow
import java.util.Calendar

//No le muevan a caseRepository es para manejar los .json
class CaseRepository(private val context: Context) {

    fun loadCases(): List<caseCliente> {
        return try {
            val jsonFile = "cases.json"
            val inputStream = context.assets.open(jsonFile)
            val reader = InputStreamReader(inputStream)
            val type = object : TypeToken<List<caseCliente>>() {}.type
            Gson().fromJson(reader, type)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }
}



@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ClientView(navController: NavController, modifier: Modifier = Modifier) {
    ClienteViewsTheme {
//        val navController = rememberNavController()
        val onSchedule: (LocalDateTime) -> Unit = { selectedDateTime ->}
        var expanded by remember { mutableStateOf(false) }

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
                        IconButton(onClick = { expanded = true }) {
                            Icon(
                                imageVector = Icons.Filled.Settings,
                                tint = Color.White,
                                contentDescription = "Settings"
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
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
                )
            }
        ) {

            innerPadding ->
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
            ) {
                ContentWithBoxes(onSchedule = {}, navController = navController)
            }
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun ContentWithBoxes(
    onSchedule: (LocalDateTime) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val caseRepository = remember { CaseRepository(context) }
    var cases by remember { mutableStateOf(emptyList<caseCliente>()) }
    var selectedCase by remember { mutableStateOf<caseCliente?>(null) }
    var showDialog by remember { mutableStateOf(false) } //No le muevan es para el pop up

    //Variables que utiliza el calendario
    var selectedDateTime by remember { mutableStateOf(LocalDateTime.now()) }
    var showDatePicker by remember { mutableStateOf(false) }
    var showTimePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val loadedCases = caseRepository.loadCases()
            cases = loadedCases
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        cases.forEach { case ->
            ContentBox(
                title = case.title,
                date = case.date,
                description = case.description,
                onClick = {
                    selectedCase = case
                    showDialog = true
                }

            )
        }

        //Pop UP
        if (showDialog && selectedCase != null) {
            AlertDialog(
                onDismissRequest = { showDialog = false },
                title = { Text(text = selectedCase!!.title) },
                text = { Text(text = selectedCase!!.description) },
                confirmButton = {
                    TextButton(onClick = { showDialog = false }) {
                        Text("Cerrar")
                    }
                },
                dismissButton = {
                    TextButton(
                        onClick = {
                            showDatePicker = true
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = BlueTEC,
                            contentColor = WhiteTEC
                        ),
                        modifier = Modifier
                            .padding(5.dp)

                    ){
                        Text(
                            text = "Agendar cita",
                            color = WhiteTEC,
                            textAlign = TextAlign.Center
                        )
                    }
                }
            )
            if (showDatePicker) {
                DatePickerDialog(
                    context,
                    { _, year, month, dayOfMonth ->
                        selectedDateTime = selectedDateTime
                            .withYear(year)
                            .withMonth(month + 1)
                            .withDayOfMonth(dayOfMonth)
                        showDatePicker = false
                        showTimePicker = true
                    },
                    selectedDateTime.year,
                    selectedDateTime.monthValue - 1,
                    selectedDateTime.dayOfMonth
                ).show()
            }

            if (showTimePicker) {
                val calendar = Calendar.getInstance()
                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        selectedDateTime = selectedDateTime
                            .withHour(hourOfDay)
                            .withMinute(minute)
                        showTimePicker = false
                        // Llamar fecha y hora
                        onSchedule(selectedDateTime)
                    },
                    selectedDateTime.hour,
                    selectedDateTime.minute,
                    true
                ).show()
            }
        }
    }
}


@Composable
fun ContentBox(title: String, date: String, description: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(8.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp),
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(12.dp)
                .wrapContentSize()
        ) {
            Row(
                modifier = Modifier
                    .padding(bottom = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = title,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    text = date,
                    fontSize = 14.sp,
                    color = Color.Black,
                    modifier = Modifier

                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            Box(
                modifier = Modifier
                    .fillMaxWidth()
//                    .weight(1f)
                    .padding(8.dp)
            ) {
                Text(
                    text = description,
                    fontSize = 14.sp,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis, // Esto nos muestra los .....
                    modifier = Modifier.padding(4.dp)

                )
            }
        }
    }
}

@Composable
fun MessagingView(navController: NavHostController) {
    var messageText by remember { mutableStateOf("") }
    val messages = remember { mutableStateListOf<Pair<String, Color>>() }

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
                placeholder = { Text("Dame tu mensaje....") }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(onClick = {
                if (messageText.isNotBlank()) {
                    messages.add(0, messageText to Color.Gray)
                    messageText = ""
                }
            }) {
                Text("Enviar")
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
