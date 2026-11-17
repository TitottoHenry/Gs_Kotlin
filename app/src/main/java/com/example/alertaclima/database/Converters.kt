package com.example.alertaclima.database

import androidx.room.TypeConverter
import com.example.alertaclima.model.AlertSeverity
import com.example.alertaclima.model.AlertStatus
import com.example.alertaclima.model.AlertType

class Converters {
    @TypeConverter
    fun fromAlertType(value: AlertType): String = value.name

    @TypeConverter
    fun toAlertType(value: String): AlertType = AlertType.valueOf(value)

    @TypeConverter
    fun fromAlertSeverity(value: AlertSeverity): String = value.name

    @TypeConverter
    fun toAlertSeverity(value: String): AlertSeverity = AlertSeverity.valueOf(value)

    @TypeConverter
    fun fromAlertStatus(value: AlertStatus): String = value.name

    @TypeConverter
    fun toAlertStatus(value: String): AlertStatus = AlertStatus.valueOf(value)
}
