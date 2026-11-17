package com.example.alertaclima.ui.theme

import androidx.compose.ui.graphics.Color
import com.example.alertaclima.model.AlertSeverity

val BluePrimary = Color(0xFF0D47A1)
val BlueContainer = Color(0xFFD6E4FF)
val BlueOnContainer = Color(0xFF001B3E)
val TealSecondary = Color(0xFF00796B)
val ScreenBackground = Color(0xFFF4F7FB)
val TextDark = Color(0xFF1A1C1E)

val SeverityLow = Color(0xFF2E7D32)
val SeverityMedium = Color(0xFFEF6C00)
val SeverityHigh = Color(0xFFC62828)

val StatusActive = Color(0xFFC62828)
val StatusResolved = Color(0xFF2E7D32)

fun severityColor(severity: AlertSeverity): Color = when (severity) {
    AlertSeverity.LOW -> SeverityLow
    AlertSeverity.MEDIUM -> SeverityMedium
    AlertSeverity.HIGH -> SeverityHigh
}
