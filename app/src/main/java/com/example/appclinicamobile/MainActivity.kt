package com.example.appclinicamobile

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.compose.runtime.livedata.observeAsState
import com.example.appclinicamobile.ui.theme.AppClinicaMobileTheme
import com.example.appclinicamobile.viewmodel.LoginViewModel
import com.example.appclinicamobile.model.UsuarioAutenticadoDTO
import com.example.appclinicamobile.view.HomeScreen
import com.example.appclinicamobile.view.LoginScreen

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    @SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppClinicaMobileTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) {
                    AppContent(loginViewModel)
                }
            }
        }
    }
}

@Composable
fun AppContent(viewModel: LoginViewModel) {
    val usuario by viewModel.usuarioAutenticado.observeAsState()

    if (usuario == null) {
        LoginScreen(viewModel = viewModel)
    } else {
        HomeScreen(
            usuario = usuario!!,
            onCerrarSesionClick = { viewModel.cerrarSesion() }
        )
    }
}
