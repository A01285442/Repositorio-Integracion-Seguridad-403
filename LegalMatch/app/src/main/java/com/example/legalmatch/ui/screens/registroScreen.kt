package com.example.proyectobueno.views

import android.app.DatePickerDialog
import android.os.Build
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.data.api.models.SendUsuario
import com.example.legalmatch.ui.screens.LoginViewModel
import com.example.legalmatch.ui.theme.AzulTec
import kotlinx.datetime.toKotlinLocalDateTime
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*


private const val TAG = "MainActivity"
@RequiresApi(Build.VERSION_CODES.O)
data class UserRegistrationData @RequiresApi(Build.VERSION_CODES.O) constructor(
    var name: String = "",
    var dateOfBirth: LocalDateTime = LocalDateTime.now(),
    var sex: String = "",
    var email: String = "",
    var password: String = ""
)


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun RegisterScreen(navController: NavController, viewModel: LoginViewModel) {
    var currentStep by remember { mutableStateOf(1) }
    var registrationData by remember { mutableStateOf(UserRegistrationData()) }
    var shouldInsert by remember { mutableStateOf(false) }

    // Mover la inserción de datos a una capa lógica separada, preferiblemente en el ViewModel
    if (shouldInsert) {
        // Lógica para insertar en Supabase
        LaunchedEffect(Unit) {
            Log.d(TAG, "Sexo: ${registrationData.sex}")
            val user = SendUsuario(
                matricula = "null",
                correo = registrationData.email,
                nombre = registrationData.name,
                fecha_nacimiento = registrationData.dateOfBirth.toKotlinLocalDateTime(),
                rol = "cliente",
                contraseña = registrationData.password,
                sexo = registrationData.sex
            )
            viewModel.registerClient(user)

            // Navegación después de un registro exitoso
            navController.navigate(Routes.Login.route) {
                // Evitar que el usuario pueda volver al registro con el botón de atrás
                popUpTo(Routes.Register.route) { inclusive = true }
            }
        }
    }

    // Control de las pantallas por pasos
    when (currentStep) {
        1 -> InputScreen(
            label = "Nombre Completo",
            value = registrationData.name,
            onValueChange = { registrationData = registrationData.copy(name = it) },
            onContinue = { currentStep = 2 }
        )
        2 -> DateOfBirthScreen(
            selectedDateTime = registrationData.dateOfBirth,
            onDateChange = { registrationData = registrationData.copy(dateOfBirth = it) },
            onContinue = { currentStep = 3 }
        )
        3 -> SexScreen(
            value = registrationData.sex,
            onValueChange = { registrationData = registrationData.copy(sex = it) },
            onContinue = { currentStep = 4 }
        )
        4 -> InputScreen(
            label = "Correo Electrónico",
            value = registrationData.email,
            onValueChange = { registrationData = registrationData.copy(email = it) },
            onContinue = { currentStep = 5 }
        )
        5 -> PasswordScreen(
            value = registrationData.password,
            onValueChange = { registrationData = registrationData.copy(password = it) },
            onContinue = { shouldInsert = true }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun GenderScreen(gender: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {
    val genderOptions = listOf("Hombre", "Mujer", "Otro")
    var expanded by remember { mutableStateOf(false) }
    var selectedGender by remember { mutableStateOf(gender) }


    RegisterTemplate(
        progress = 3,
        currentStep = 3,
        label = "Cual es tu género",
        value = selectedGender,
        onValueChange = onValueChange,
        onContinue = onContinue,
        inputType = InputType.TYPE_MASK_CLASS,

    )
    //{
//        Box(modifier = Modifier.fillMaxWidth()) {
//            ExposedDropdownMenuBox(
//                expanded = expanded,
//                onExpandedChange = {
//                    expanded = !expanded
//                }
//            ) {
//                TextField(
//                    readOnly = true,
//                    value = selectedGender,
//                    onValueChange = { },
//                    label = { Text("Selecciona tu género") },
//                    trailingIcon = {
//                        ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded)
//                    },
//                    colors = ExposedDropdownMenuDefaults.textFieldColors(),
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .menuAnchor()
//                        .clickable { expanded = !expanded }
//                )
//                ExposedDropdownMenu(
//                    expanded = expanded,
//                    onDismissRequest = {
//                        expanded = false
//                    }
//                ) {
//                    genderOptions.forEach { option ->
//                        DropdownMenuItem(
//                            onClick = {
//                                selectedGender = option
//                                onValueChange(option)
//                                expanded = false      
//                            },
//                            text = { Text(text = option) }
//                        )
//                    }
//                }
//            }
//        }
//    }
}

@Composable
fun EmailScreen(email: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 4,
        currentStep = 4,
        label = "Tu E-mail",
        value = email,
        onValueChange = onValueChange,
        onContinue = onContinue,
        inputType = InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS
    )

}

@Composable
fun PasswordScreen(password: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 5,
        currentStep = 5,
        label = "Tu contraseña",
        value = password,
        onValueChange = onValueChange,
        visualTransformation = PasswordVisualTransformation(),
        onContinue = onContinue,
        inputType = InputType.TYPE_TEXT_VARIATION_PASSWORD
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterTemplate(
    progress: Int,
    currentStep: Int,
    label: String,
    value: String,
    onValueChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    val isValid = value.isNotBlank()

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = label,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AzulTec
            )

            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text(label) },
                    visualTransformation = visualTransformation,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true
            )

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = { if (isValid) onContinue() },
                enabled = isValid,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isValid) AzulTec else Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Continuar")
            }
        }
    }
}


@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DateOfBirthScreen(
    selectedDateTime: LocalDateTime?, // Modificamos el tipo a LocalDateTime?
    onDateChange: (LocalDateTime) -> Unit, // Devolvemos un LocalDateTime
    onContinue: () -> Unit
) {
    val calendar = Calendar.getInstance()

    // Mantenemos la fecha seleccionada como LocalDateTime
    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _, year, month, dayOfMonth ->
            // Configuramos el calendar con la fecha seleccionada
            calendar.set(year, month, dayOfMonth)

            // Convertimos el Calendar a LocalDateTime
            val zone = ZoneId.of("America/Edmonton")
            val selectedDate = LocalDateTime.ofInstant(calendar.time.toInstant(), zone)

            // Actualizamos el valor de LocalDateTime
            onDateChange(selectedDate)
        },
        calendar.get(Calendar.YEAR),
        calendar.get(Calendar.MONTH),
        calendar.get(Calendar.DAY_OF_MONTH)
    )

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar el contenido verticalmente
        ) {
            // Texto para el campo de fecha de nacimiento
            Text(
                text = "Fecha de nacimiento",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = AzulTec
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón para seleccionar la fecha de nacimiento
            OutlinedButton(
                onClick = { datePickerDialog.show() },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Text(
                    text = selectedDateTime?.toString() ?: "Seleccionar fecha",
                    color = Color.Black
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de continuar
            Button(
                onClick = {
                    if (selectedDateTime != null) {
                        onContinue()
                    }
                },
                enabled = selectedDateTime != null, // Desactivar el botón si no se selecciona fecha
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (selectedDateTime != null) AzulTec else Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Continuar")
            }
        }
    }
}

@Composable
fun SexScreen(value: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar el contenido verticalmente
        ) {
            // Título de la pantalla
            Text(
                text = "Selecciona tu sexo",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Opciones de selección de sexo
            GenderRadioButton(
                selectedGender = value,
                onGenderSelected = { gender ->
                    onValueChange(gender)
                }
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Botón de continuar
            Button(
                onClick = {
                    if (value.isNotEmpty()) {
                        onContinue()
                    }
                },
                enabled = value.isNotEmpty(), // Desactivar si no se selecciona nada
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (value.isNotEmpty()) AzulTec else Color.Gray,
                    contentColor = Color.White
                )
            ) {
                Text(text = "Continuar")
            }
        }
    }
}
@Composable
fun PasswordScreen(
    value: String,
    onValueChange: (String) -> Unit,
    onContinue: () -> Unit
) {
    var isNameValid = false
    var contraseña2 by remember { mutableStateOf("") }

    Scaffold { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center // Centrar contenido verticalmente
        ) {

            Text(
                text = "Crea una contraseña",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold
            )
            // Campo de texto para el nombre
            OutlinedTextField(
                value = value,
                onValueChange = onValueChange,
                label = { Text("Contraseña") }, // Aseguramos que el texto del label sea un Text composable
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true
            )
            OutlinedTextField(
                value = contraseña2,
                onValueChange = { contraseña2 = it },
                label = { Text("Repite Contraseña") }, // Aseguramos que el texto del label sea un Text composable
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                singleLine = true
            )
            if (value.isBlank()){

            } else if (value != contraseña2){
                Text("Las contraseñas deben ser iguales")
            } else if (value.length < 8){
                Text("La contraseña debe ser de 8 caracteres mínimo")
            } else {
                isNameValid = true
            }

            // Espaciado
            Spacer(modifier = Modifier.height(16.dp))

            // Botón de continuar
            Button(
                onClick = {
                    if (isNameValid) {
                        onContinue()
                    }
                },
                enabled = isNameValid, // Desactivar el botón si el nombre está vacío
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (isNameValid) AzulTec else Color.Gray, // Cambiar color según estado
                    contentColor = Color.White
                )
            ) {
                Text(text = "Finalizar")
            }
        }
    }
}

@Composable
fun GenderRadioButton(selectedGender: String, onGenderSelected: (String) -> Unit) {
    val genderOptions = listOf("hombre", "mujer")

    // Mostrar las opciones de sexo como Radio Buttons
    genderOptions.forEach { gender ->
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            RadioButton(
                selected = selectedGender == gender,
                onClick = { onGenderSelected(gender) }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = gender)
        }
    }
}