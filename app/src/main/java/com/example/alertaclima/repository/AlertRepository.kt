package com.example.alertaclima.repository

import com.example.alertaclima.database.AlertDao
import com.example.alertaclima.model.AlertEntity
import kotlinx.coroutines.flow.Flow


class AlertRepository(private val dao: AlertDao) {

    val alerts: Flow<List<AlertEntity>> = dao.observeAll()

    suspend fun insert(alert: AlertEntity) {
        dao.insert(alert)
    }

    suspend fun update(alert: AlertEntity) {
        dao.update(alert)
    }

    suspend fun delete(alert: AlertEntity) {
        dao.delete(alert)
    }
}
