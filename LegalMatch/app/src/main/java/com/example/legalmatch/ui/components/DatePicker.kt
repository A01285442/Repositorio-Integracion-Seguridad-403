package com.example.legalmatch.ui.components

import android.app.DatePickerDialog
import android.os.Build
import android.widget.DatePicker
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun DatePicker(
    selectedDateTime: LocalDateTime?,          // La fecha seleccionada
    onDateChange: (LocalDateTime) -> Unit      // Callback cuando la fecha cambia
) {
    val context = LocalContext.current

    // Estado para manejar la fecha seleccionada
    var selectedDate by remember { mutableStateOf(selectedDateTime) }

    // Obtener la fecha actual para establecer valores iniciales
    val now = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    // Mostrar un DatePickerDialog cuando se hace clic en el botón
    val datePickerDialog = remember {
        DatePickerDialog(
            context,
            { _: DatePicker, year: Int, month: Int, dayOfMonth: Int ->
                // Crear un nuevo LocalDateTime basado en la selección
                val newDateTime = LocalDateTime(year, month + 1, dayOfMonth, 0, 0)
                selectedDate = newDateTime
                onDateChange(newDateTime) // Llamar al callback con la fecha nueva
            },
            selectedDate?.year ?: now.year,        // Año inicial
            (selectedDate?.month?.value ?: now.monthNumber) - 1,  // Mes inicial (meses son 0-11)
            selectedDate?.dayOfMonth ?: now.dayOfMonth // Día inicial
        )
    }.apply {
        // ???
    }


    // Botón para seleccionar la fecha
    OutlinedButton(
        onClick = { datePickerDialog.show() },
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp)
    ) {
        // Mostrar la fecha seleccionada o un texto inicial
        if (selectedDate == null) {
            Text("Seleccionar fecha", color = MaterialTheme.colorScheme.onBackground)
        } else {
            // Mostrar la fecha seleccionada en formato Día Mes Año
            Text("${selectedDate!!.dayOfMonth} ${selectedDate!!.month.name.lowercase().capitalize()} ${selectedDate!!.year}")
        }
    }

}