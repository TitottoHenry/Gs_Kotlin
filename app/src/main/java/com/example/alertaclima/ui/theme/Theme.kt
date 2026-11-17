package com.example.alertaclima.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = BluePrimary,
    onPrimary = Color.White,
    primaryContainer = BlueContainer,
    onPrimaryContainer = BlueOnContainer,
    secondary = TealSecondary,
    onSecondary = Color.White,
    background = ScreenBackground,
    onBackground = TextDark,
    surface = Color.White,
    onSurface = TextDark
)

@Composable
fun AlertaClimaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = LightColors,
        content = content
    )
}
