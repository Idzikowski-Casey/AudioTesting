package com.application.audiotesting.ui.theme

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

// Light Theme
val LightColorScheme = lightColorScheme(
    primary = OceanTeal,
    secondary = ForestGreen,
    tertiary = Sage,
    background = Color(0xFFFFFBFE),
    surface = Sand,
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.Black,
    onBackground = ForestGreen,
    onSurface = ForestGreen
)

// Dark Theme
val DarkColorScheme = darkColorScheme(
    primary = MistBlue,
    secondary = Moon,
    tertiary = Sand,
    background = ForestGreen,
    surface = Color(0xFF2C2C2C),
    onPrimary = Color.Black,
    onSecondary = Color.Black,
    onTertiary = Color.Black,
    onBackground = Color.White,
    onSurface = Color.White
)

@Composable
fun AudioTestingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
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