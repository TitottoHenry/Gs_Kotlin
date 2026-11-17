package com.example.alertaclima.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.alertaclima.ui.screens.AlertFormScreen
import com.example.alertaclima.ui.screens.AlertListScreen
import com.example.alertaclima.ui.screens.HomeScreen
import com.example.alertaclima.viewmodel.AlertViewModel

object Routes {
    const val HOME = "home"
    const val LIST = "list"
    const val FORM = "form"
}

@Composable
fun AppNavigation(
    viewModel: AlertViewModel,
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val formState by viewModel.formState.collectAsStateWithLifecycle()

    NavHost(navController = navController, startDestination = Routes.HOME) {

        composable(Routes.HOME) {
            HomeScreen(
                uiState = uiState,
                onOpenList = {
                    navController.navigate(Routes.LIST) { launchSingleTop = true }
                },
                onNewAlert = {
                    viewModel.startNewForm()
                    navController.navigate(Routes.FORM) { launchSingleTop = true }
                }
            )
        }

        composable(Routes.LIST) {
            AlertListScreen(
                uiState = uiState,
                messages = viewModel.messages,
                onBack = { navController.popBackStack() },
                onNewAlert = {
                    viewModel.startNewForm()
                    navController.navigate(Routes.FORM) { launchSingleTop = true }
                },
                onEdit = { alert ->
                    viewModel.startEditForm(alert)
                    navController.navigate(Routes.FORM) { launchSingleTop = true }
                },
                onToggleStatus = { alert -> viewModel.toggleStatus(alert) },
                onDelete = { alert -> viewModel.deleteAlert(alert) }
            )
        }

        composable(Routes.FORM) {
            AlertFormScreen(
                formState = formState,
                onTitleChange = viewModel::onTitleChange,
                onLocationChange = viewModel::onLocationChange,
                onDescriptionChange = viewModel::onDescriptionChange,
                onTypeChange = viewModel::onTypeChange,
                onSeverityChange = viewModel::onSeverityChange,
                onSave = {
                    viewModel.saveAlert {

                        navController.navigate(Routes.LIST) {
                            popUpTo(Routes.HOME)
                            launchSingleTop = true
                        }
                    }
                },
                onCancel = { navController.popBackStack() }
            )
        }
    }
}
