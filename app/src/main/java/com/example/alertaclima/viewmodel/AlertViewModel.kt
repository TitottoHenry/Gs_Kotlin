package com.example.alertaclima.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.alertaclima.database.AlertDatabase
import com.example.alertaclima.model.AlertEntity
import com.example.alertaclima.model.AlertSeverity
import com.example.alertaclima.model.AlertStatus
import com.example.alertaclima.model.AlertType
import com.example.alertaclima.repository.AlertRepository
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AlertViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AlertRepository(
        AlertDatabase.getInstance(application).alertDao()
    )

    /** Lista de alertas vinda do Room. Atualiza sozinha a cada insert/update/delete. */
    val uiState: StateFlow<AlertListUiState> = repository.alerts
        .map { alerts -> AlertListUiState(alerts = alerts, isLoading = false) }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = AlertListUiState()
        )

    private val _formState = MutableStateFlow(AlertFormState())
    val formState: StateFlow<AlertFormState> = _formState.asStateFlow()

    // Mensagens de sucesso exibidas em Snackbar (ficam em fila até a tela coletar).
    private val _messages = Channel<String>(Channel.BUFFERED)
    val messages: Flow<String> = _messages.receiveAsFlow()

    // ---------- Formulário ----------

    fun startNewForm() {
        _formState.value = AlertFormState()
    }

    fun startEditForm(alert: AlertEntity) {
        _formState.value = AlertFormState(
            editingId = alert.id,
            title = alert.title,
            location = alert.location,
            description = alert.description,
            type = alert.type,
            severity = alert.severity,
            status = alert.status,
            createdAt = alert.createdAt
        )
    }

    fun onTitleChange(value: String) {
        _formState.update { it.copy(title = value, titleError = null) }
    }

    fun onLocationChange(value: String) {
        _formState.update { it.copy(location = value, locationError = null) }
    }

    fun onDescriptionChange(value: String) {
        _formState.update { it.copy(description = value) }
    }

    fun onTypeChange(value: AlertType) {
        _formState.update { it.copy(type = value) }
    }

    fun onSeverityChange(value: AlertSeverity) {
        _formState.update { it.copy(severity = value) }
    }

    /**
     * Valida os campos obrigatórios. Se algum for inválido, NÃO salva e mostra o erro.
     * Se tudo estiver certo, grava no Room (insert ou update) e chama [onSuccess].
     */
    fun saveAlert(onSuccess: () -> Unit) {
        val form = _formState.value
        if (form.isSaving) return

        val titleError = if (form.title.isBlank()) "Informe o título do alerta." else null
        val locationError = if (form.location.isBlank()) "Informe o local da ocorrência." else null

        if (titleError != null || locationError != null) {
            _formState.update {
                it.copy(titleError = titleError, locationError = locationError)
            }
            return
        }

        _formState.update { it.copy(isSaving = true) }
        viewModelScope.launch {
            val alert = AlertEntity(
                id = form.editingId ?: 0L,
                title = form.title.trim(),
                location = form.location.trim(),
                description = form.description.trim(),
                type = form.type,
                severity = form.severity,
                status = form.status,
                createdAt = if (form.isEditing) form.createdAt else System.currentTimeMillis()
            )
            if (form.isEditing) {
                repository.update(alert)
                _messages.send("Alerta atualizado com sucesso.")
            } else {
                repository.insert(alert)
                _messages.send("Alerta registrado com sucesso.")
            }
            _formState.update { it.copy(isSaving = false) }
            onSuccess()
        }
    }

    // ---------- Ações sobre registros ----------

    fun toggleStatus(alert: AlertEntity) {
        viewModelScope.launch {
            val resolving = alert.status == AlertStatus.ACTIVE
            val newStatus = if (resolving) AlertStatus.RESOLVED else AlertStatus.ACTIVE
            repository.update(alert.copy(status = newStatus))
            _messages.send(
                if (resolving) "Alerta marcado como resolvido." else "Alerta reaberto."
            )
        }
    }

    fun deleteAlert(alert: AlertEntity) {
        viewModelScope.launch {
            repository.delete(alert)
            _messages.send("Alerta excluído.")
        }
    }
}
