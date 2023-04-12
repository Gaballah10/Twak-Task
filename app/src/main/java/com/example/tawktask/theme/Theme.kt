package com.example.tawktask.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import com.example.tawktask.ui.theme.*

private val DarkColorPalette = darkColors(
    primary = PrimaryDesign,
    primaryVariant = ModifiedDesign,
    secondary = ModifiedDesign
)

private val LightColorPalette = lightColors(
    primary = AccentDesign,
    primaryVariant = ModifiedDesign,
    secondary = ModifiedDesign

    /* Other default colors to override
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun TawkTaskTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}