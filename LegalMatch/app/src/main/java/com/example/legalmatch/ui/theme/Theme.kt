package com.example.legalmatch.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

// Primary = Color de los botones
// Secondary = Color del texto sobre el fondo
// Tertiary = Color del texto sobre los botones

/*
Primary = Un color primario brillante, pero no demasiado saturado
Secondary = Color secundario más brillante para destacar botones

Background = Fondo gris oscuro en lugar de negro puro
Surface = Para tarjetas elevadas o superficies
onPrimary = Texto sobre color primario
onSecondary = Texto sobre color secundario
onBackground = Texto sobre fondo oscuro
onSurface = Texto sobre superficies elevadas
 */

val Murrey = Color(0xFF82204A)
val RichBlack = Color(0xFF071123)
val OxfordBLue = Color(0xFF172845)
val VistaBlue = Color(0xFF8CA4D3)
val DavysGray = Color(0xFF4E4B5C)

val Sapphire = Color(0xFF2050b3)
val Beige = Color(0xFFF4F5E0)
val GhostWhite = Color(0xFFF9FBFF)

private val DarkColorScheme = darkColorScheme(
    primary = Murrey,
    onPrimary = Color.White,

    secondary = VistaBlue,
    onSecondary = Color.White,

    tertiary = Color.Black,
    onTertiary = Color.Gray,

    background = RichBlack,
    onBackground = Color.White,

    surface = OxfordBLue,
    onSurface = Color.White,

    error = DavysGray
)

private val LightColorScheme = lightColorScheme(
    primary = Sapphire,
    onPrimary = Color.White,

    secondary = Color.Black,
    onSecondary = Color.White,

    tertiary = Color.White,
    onTertiary = Color.Gray,

    background = GhostWhite,
    onBackground = Color.Black,

    surface = Color.White,
    onSurface = Color.Black,

    error = Color.Red
)

@Composable
fun LegalMatchTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}