package com.example.alertaclima.viewmodel

import com.example.alertaclima.model.AlertEntity
import com.example.alertaclima.model.AlertSeverity
import com.example.alertaclima.model.AlertStatus
import com.example.alertaclima.model.AlertType

/** Estado da listagem: lista vinda do Room e indicador de carregamento. */
data class AlertListUiState(
    val alerts: List<AlertEntity> = emptyList(),
    val isLoading: Boolean = true
) {
    val activeCount: Int get() = alerts.count { it.status == AlertStatus.ACTIVE }
    val resolvedCount: Int get() = alerts.count { it.status == AlertStatus.RESOLVED }
}

/** Estado do formulário de cadastro/edição (campos, erros de validação e modo de edição). */
data class AlertFormState(
    val editingId: Long? = null,
    val title: String = "",
    val location: String = "",
    val description: String = "",
    val type: AlertType = AlertType.FLOOD,
    val severity: AlertSeverity = AlertSeverity.MEDIUM,
    val status: AlertStatus = AlertStatus.ACTIVE,
    val createdAt: Long = 0L,
    val titleError: String? = null,
    val locationError: String? = null,
    val isSaving: Boolean = false
) {
    val isEditing: Boolean get() = editingId != null
}
