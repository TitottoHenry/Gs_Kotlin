package com.example.alertaclima.model

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey(autoGenerate = true) val id: Long = 0,
    val title: String,
    val location: String,
    val description: String,
    val type: AlertType,
    val severity: AlertSeverity,
    val status: AlertStatus,
    val createdAt: Long
)
