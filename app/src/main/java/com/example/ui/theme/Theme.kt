package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val TitanColorScheme = darkColorScheme(
  primary = TitanPrimary,
  secondary = TitanSecondary,
  tertiary = TitanAccent,
  background = TitanBackground,
  surface = TitanSurface,
  surfaceVariant = TitanSurfaceVariant,
  onPrimary = TitanOnPrimary,
  onBackground = TitanOnBackground,
  onSurface = TitanOnSurface,
  outline = TitanBorder,
  error = TitanError
)

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  // Disable dynamic system color to force our premium bespoke branding
  dynamicColor: Boolean = false,
  content: @Composable () -> Unit,
) {
  val colorScheme = if (dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
    val context = LocalContext.current
    if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
  } else {
    TitanColorScheme
  }

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}
