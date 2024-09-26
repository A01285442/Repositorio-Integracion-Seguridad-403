package com.example.proyectobueno.views

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

import com.example.proyectobueno.R
import com.example.proyectobueno.ui.theme.ClienteViewsTheme

//APIs Jorge
//import retrofit2.Call
//import retrofit2.http.GET
//import retrofit2.Retrofit
//import retrofit2.converter.gson.GsonConverterFactory
//import retrofit2.HttpException
import java.io.IOException

//.json
import android.content.Context
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.clickable
import androidx.compose.material3.AlertDialogDefaults.containerColor
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import com.example.proyectobueno.models.caseCliente
import com.example.proyectobueno.ui.theme.BlueTEC
import com.example.proyectobueno.ui.theme.WhiteTEC

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.InputStreamReader
import java.time.LocalDateTime

//cosas del calendario
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.ui.text.style.TextOverflow
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.gotrue.Auth
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.postgrest.postgrest
import io.github.jan.supabase.postgrest.from
import kotlinx.coroutines.launch
import kotlinx.serialization.Serializable
import java.text.SimpleDateFormat
//import retrofit2.awaitResponse
import java.util.Calendar
import java.util.Locale


@Serializable
data class Date(
    val fecha: String
)
val supabaseCliente = createSupabaseClient(
    supabaseUrl = "https://wbhyplodhfxcyeochnpf.supabase.co",
    supabaseKey = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpc3MiOiJzdXBhYmFzZSIsInJlZiI6IndiaHlwbG9kaGZ4Y3llb2NobnBmIiwicm9sZSI6ImFub24iLCJpYXQiOjE3MjU0MjExNTUsImV4cCI6MjA0MDk5NzE1NX0.sqMEMPdCx9u-kC0TI0OLWKm1KXZjSkeDS1N3bQiG-jI"
) {
    install(Auth)
    install(Postgrest)
}

val idCliente = 1

//No le muevan a caseRepository es para manejar los .json
class CaseRepository(private val context: Context) {
    suspend fun loadCases(): List<caseCliente> {
        val cases = supabaseCliente.from("asesorias").select().decodeList<caseCliente>()
        println(cases.size)
        return cases
    }
}

//Insert DB Calendario
suspend fun insertDate(selectedDate: Long) {
    try {
        val dateString = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSSXXX", Locale.getDefault()).format(
            java.util.Date(selectedDate)
        )
        val response = supabaseCliente.from("asesorias")
            .insert(mapOf("fecha" to dateString))
            .decodeList<Date>()

    } catch (e: Exception) {
        println("Error al insertar la fecha: ${e.message}")
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
                        IconButton(onClick = { navController.navigate("MessagingView") }) {
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
    onSchedule: (String) -> Unit,
    navController: NavController
) {
    val context = LocalContext.current
    val caseRepository = remember { CaseRepository(context) }
    var cases by remember { mutableStateOf(emptyList<caseCliente>()) }
    var selectedCase by remember { mutableStateOf<caseCliente?>(null) }
    var showDialog by remember { mutableStateOf(false) } //No le muevan es para el pop up
    var showDatePicker by remember { mutableStateOf(false) }

    LaunchedEffect(Unit) {
        try {
            val loadedCases = caseRepository.loadCases() // Now this is a suspend function
            cases = loadedCases // Assign the loaded cases to the state variable
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    val scrollState = rememberScrollState()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(cases) { case ->
            println(case.titulo)
            println(case.fecha)
            println(case.descripcion)
            ContentBox(
                title = case.titulo,
                date = case.fecha,
                description = case.descripcion,
                onClick = {
                    selectedCase = case
                    showDialog = true
                }

            )
            println("Test2")
        }
    }

        //Pop UP
    if (showDialog && selectedCase != null) {
        AlertDialog(
            onDismissRequest = { showDialog = false },
            title = { Text(text = selectedCase!!.titulo) },
            text = { Text(text = selectedCase!!.descripcion) },
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
        if (showDatePicker && selectedCase != null) {
            openCalendar(selectedCase!!) { formattedDate ->
                onSchedule(formattedDate)
                showDatePicker = false
                showDialog = false
            }
        }
    }
}

@Composable
private fun openCalendar(case: caseCliente, onSchedule: (String) -> Unit) {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()
    var showDatePicker by remember { mutableStateOf(true) }
    var selectedDate by remember { mutableStateOf("") }
    val coroutineScope = rememberCoroutineScope()

    if (showDatePicker) {
        DatePickerDialog(
            context,
            { _, year, month, dayOfMonth ->
                val selectedCalendar = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }

                TimePickerDialog(
                    context,
                    { _, hourOfDay, minute ->
                        selectedCalendar.apply {
                            set(Calendar.HOUR_OF_DAY, hourOfDay)
                            set(Calendar.MINUTE, minute)
                        }
                        val timestamp = selectedCalendar.timeInMillis

                        coroutineScope.launch {
                            insertDate(timestamp)
                        }

                        val formattedDateTime = "$dayOfMonth/${month + 1}/$year $hourOfDay:$minute"
                        onSchedule(formattedDateTime)
                    },
                    calendar.get(Calendar.HOUR_OF_DAY),
                    calendar.get(Calendar.MINUTE),
                    true
                ).show()

                showDatePicker = false
            },
            calendar.get(Calendar.YEAR),
            calendar.get(Calendar.MONTH),
            calendar.get(Calendar.DAY_OF_MONTH)
        ).show()
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
