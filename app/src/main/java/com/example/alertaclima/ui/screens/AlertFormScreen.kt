package com.example.alertaclima.ui.screens

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alertaclima.model.AlertSeverity
import com.example.alertaclima.model.AlertType
import com.example.alertaclima.viewmodel.AlertFormState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlertFormScreen(
    formState: AlertFormState,
    onTitleChange: (String) -> Unit,
    onLocationChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
    onTypeChange: (AlertType) -> Unit,
    onSeverityChange: (AlertSeverity) -> Unit,
    onSave: () -> Unit,
    onCancel: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = if (formState.isEditing) "Editar alerta" else "Novo alerta",
                        fontWeight = FontWeight.Bold
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onCancel) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Voltar"
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            OutlinedTextField(
                value = formState.title,
                onValueChange = onTitleChange,
                label = { Text("Título *") },
                singleLine = true,
                isError = formState.titleError != null,
                supportingText = if (formState.titleError != null) {
                    { Text(formState.titleError) }
                } else null,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = formState.location,
                onValueChange = onLocationChange,
                label = { Text("Local (bairro, rua ou referência) *") },
                singleLine = true,
                isError = formState.locationError != null,
                supportingText = if (formState.locationError != null) {
                    { Text(formState.locationError) }
                } else null,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = "Tipo de ocorrência",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Row(
                modifier = Modifier.horizontalScroll(rememberScrollState()),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AlertType.entries.forEach { type ->
                    FilterChip(
                        selected = formState.type == type,
                        onClick = { onTypeChange(type) },
                        label = { Text(type.label) }
                    )
                }
            }

            Text(
                text = "Gravidade",
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Bold
            )
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                AlertSeverity.entries.forEach { severity ->
                    FilterChip(
                        selected = formState.severity == severity,
                        onClick = { onSeverityChange(severity) },
                        label = { Text(severity.label) }
                    )
                }
            }

            OutlinedTextField(
                value = formState.description,
                onValueChange = onDescriptionChange,
                label = { Text("Descrição (opcional)") },
                minLines = 3,
                modifier = Modifier.fillMaxWidth()
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                OutlinedButton(
                    onClick = onCancel,
                    modifier = Modifier.weight(1f)
                ) {
                    Text("Cancelar")
                }
                Button(
                    onClick = onSave,
                    enabled = !formState.isSaving,
                    modifier = Modifier.weight(1f)
                ) {
                    Text(if (formState.isEditing) "Salvar alterações" else "Salvar alerta")
                }
            }
        }
    }
}
