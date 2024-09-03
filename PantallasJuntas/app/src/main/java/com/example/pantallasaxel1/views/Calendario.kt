package com.example.pantallasaxel1.views

import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import com.example.pantallasaxel1.ui.theme.CalendarTheme

@Composable
fun calendarButton(){
    CalendarTheme{
        var showDate by remember { mutableStateOf()}

    }
}