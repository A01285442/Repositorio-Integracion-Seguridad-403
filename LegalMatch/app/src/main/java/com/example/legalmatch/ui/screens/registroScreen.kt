package com.example.proyectobueno.views

import android.text.InputType
import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.app.navigation.Routes
import com.example.legalmatch.data.api.models.SendUsuario
import com.example.legalmatch.data.api.models.Usuario
import com.example.legalmatch.ui.screens.LoginViewModel
import com.example.legalmatch.ui.theme.AzulTec
import kotlinx.datetime.LocalDateTime

private const val TAG = "MainActivity"

@Composable
fun RegisterScreen(navController: NavController, viewModel: LoginViewModel) {
        var currentStep by remember { mutableStateOf(1) }

        // Variables para almacenar los datos del usuario
        var name by remember { mutableStateOf("") }
        var dateBirth by remember { mutableStateOf("") }
        var gender by remember { mutableStateOf("") }
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }

        // Estado para controlar cuando insertar
        var shouldInsert by remember { mutableStateOf(false) }

        when (currentStep) {
            1 -> NameScreen(name = name, onValueChange = { name = it }, onContinue = {
                Log.d(TAG, "xdxd")
                currentStep = 2 })
            2 -> DateOfBirthScreen(dateBirth = dateBirth, onValueChange = { dateBirth = it }, onContinue = { currentStep = 3 })
            3 -> GenderScreen(gender = gender, onValueChange = { gender = it }, onContinue = { currentStep = 4 })
            4 -> EmailScreen(email = email, onValueChange = { email = it }, onContinue = { currentStep = 5 })
            5 -> PasswordScreen(password = password, onValueChange = { password = it }, onContinue = {
                shouldInsert = true
            })
        }



        // Lógica para insertar en Supabase
        if (shouldInsert) {
            val user = SendUsuario(
                matricula = "null",
                correo = email,
                nombre = name,
                fecha_nacimiento = LocalDateTime(1999,5,5, 5, 5, 5),
                rol = "cliente",
                contraseña = password,
                sexo = "hombre"
            )
            viewModel.registerClient(user)
            navController.navigate(Routes.Login.route)
        }

}

@Composable
fun NameScreen(name: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 1,
        currentStep = 1,
        label = "Nombre completo",
        value = name,
        onValueChange = onValueChange,
        onContinue = onContinue,
        inputType = InputType.TYPE_TEXT_VARIATION_PERSON_NAME
    )
}

@Composable
fun DateOfBirthScreen(dateBirth: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    var showDatePicker by remember { mutableStateOf(false) }

    RegisterTemplate(
        progress = 2,
        currentStep = 2,
        label = "Fecha de nacimiento",
        value = dateBirth,
        onValueChange = { }, // Deshabilitamos la entrada directa
        onContinue = onContinue,
        inputType = InputType.TYPE_DATETIME_VARIATION_DATE,
        onClick = { showDatePicker = true } // Al hacer click, mostramos el DatePicker
    )

    if (showDatePicker) {
        DatePickerModalInput(
            onDateSelected = { dateInMillis ->
                val dateFormatted = dateInMillis?.let {
                    // Convertimos el tiempo en milisegundos a un formato legible, por ejemplo "YYYY-MM-DD"
                    java.text.SimpleDateFormat("yyyy-MM-dd").format(java.util.Date(it))
                } ?: ""
                onValueChange(dateFormatted) // Actualizamos la variable `dateBirth`
                showDatePicker = false // Cerramos el modal después de seleccionar
            },
            onDismiss = { showDatePicker = false }
        )
    }
}


@Composable
fun GenderScreen(gender: String, onValueChange: (String) -> Unit, onContinue: () -> Unit) {

    RegisterTemplate(
        progress = 3,
        currentStep = 3,
        label = "Cual es tu género",
        value = gender,
        onValueChange = onValueChange,
        onContinue = onContinue,
        inputType = InputType.TYPE_MASK_CLASS
    )
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
    visualTransformation: VisualTransformation = VisualTransformation.None,
    onValueChange: (String) -> Unit,
    onContinue: () -> Unit,
    inputType: Int,
    onClick: () -> Unit = {}  // Añadimos un callback para manejar el click en el campo de fecha
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text (
                        text = "Registro",
                        color = AzulTec,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // ... Otras partes del UI

            // Mostrar un campo que se puede hacer clic para fechas
            if (inputType == InputType.TYPE_DATETIME_VARIATION_DATE) {
                OutlinedTextField(
                    value = value,
                    onValueChange = { /* Nada, no permitimos edición directa */ },
                    label = { Text(label) },
                    readOnly = true,  // Lo marcamos como solo lectura
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                        .clickable { onClick() }  // Al hacer clic, mostramos el DatePicker
                )
            } else {
                // Otros tipos de campos
                OutlinedTextField(
                    value = value,
                    onValueChange = onValueChange,
                    label = { Text(label) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 8.dp)
                )
            }

            // Botón de continuar
            Button(
                onClick = onContinue,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                colors = ButtonColors(AzulTec, Color.White, Color.Gray, Color.Gray)
            ) {
                Text("Continue")
            }

            // ... Otras partes del UI
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DatePickerModalInput(
    onDateSelected: (Long?) -> Unit,
    onDismiss: () -> Unit
) {
    val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Input)

    DatePickerDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(onClick = {
                onDateSelected(datePickerState.selectedDateMillis)
                onDismiss()
            }) {
                Text("OK")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Cancel")
            }
        }
    ) {
        DatePicker(state = datePickerState)
    }
}