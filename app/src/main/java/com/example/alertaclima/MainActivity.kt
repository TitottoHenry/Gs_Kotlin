package com.example.alertaclima

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.alertaclima.ui.navigation.AppNavigation
import com.example.alertaclima.ui.theme.AlertaClimaTheme
import com.example.alertaclima.viewmodel.AlertViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AlertaClimaTheme {

                val viewModel: AlertViewModel = viewModel()
                AppNavigation(viewModel = viewModel)
            }
        }
    }
}
