package com.example.appclinicamobile

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.appclinicamobile.ui.theme.AppClinicaMobileTheme
import com.example.appclinicamobile.viewmodel.LoginViewModel
import com.example.appclinicamobile.navigation.NavRoutes
import com.example.appclinicamobile.view.HomeScreen
import com.example.appclinicamobile.view.LoginScreen
import com.example.appclinicamobile.view.PacientesScreen
import com.example.appclinicamobile.view.ProfesionalesScreen
import com.example.appclinicamobile.view.TurnosScreen

class MainActivity : ComponentActivity() {
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AppClinicaMobileTheme {
                val navController = rememberNavController()
                val usuario by loginViewModel.usuarioAutenticado.observeAsState()

                LaunchedEffect(usuario) {
                    if (usuario == null) {
                        navController.navigate(NavRoutes.Login) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    } else {
                        navController.navigate(NavRoutes.Home) {
                            popUpTo(navController.graph.startDestinationId) {
                                inclusive = true
                            }
                            launchSingleTop = true
                        }
                    }
                }

                NavHost(
                    navController = navController,
                    startDestination = NavRoutes.Login
                ) {
                    composable(NavRoutes.Login) {
                        LoginScreen(viewModel = loginViewModel)
                    }
                    composable(NavRoutes.Home) {
                        val usuarioLocal = usuario
                        if (usuarioLocal != null) {
                            HomeScreen(
                                usuario = usuarioLocal,
                                onVerTurnosClick = { navController.navigate(NavRoutes.Turnos) },
                                onVerPacientesClick = { navController.navigate(NavRoutes.Pacientes) },
                                onVerProfesionalesClick = { navController.navigate(NavRoutes.Profesionales) },
                                onCerrarSesionClick = { loginViewModel.cerrarSesion() }
                            )
                        } else {
                            LaunchedEffect(Unit) {
                                navController.navigate(NavRoutes.Login) {
                                    popUpTo(navController.graph.startDestinationId) { inclusive = true }
                                    launchSingleTop = true
                                }
                            }
                        }
                    }
                    composable(NavRoutes.Turnos) {
                        TurnosScreen()
                    }
                    composable(NavRoutes.Pacientes) {
                        PacientesScreen()
                    }
                    composable(NavRoutes.Profesionales) {
                        ProfesionalesScreen()
                    }
                }

            }
        }
    }
}

