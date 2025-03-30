package ph.edu.cksc.college.appdev.appdev2025.screens

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch
import ph.edu.cksc.college.appdev.appdev2025.MAIN_SCREEN
import ph.edu.cksc.college.appdev.appdev2025.service.AuthService

@Composable
fun AuthScreen(
    navController: NavHostController,
    auth: FirebaseAuth, firestore: FirebaseFirestore
) {
    val authService = AuthService(LocalContext.current, auth, firestore)
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    val userPass by remember { mutableStateOf(authService.readUser()) }

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Log.d("Email", userPass.email)
    if (userPass.email != "") {
        authService.loginUser(
            userPass.email, userPass.password,
            onSuccess = { message: String ->
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                    navController.navigate(MAIN_SCREEN)
                }
            },
            onFailure = { message: String ->
                scope.launch {
                    snackbarHostState.showSnackbar(message)
                }
            }
        )
    }

    Scaffold(
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        }
    ) { contentPadding ->
        // Screen content
        Column(
            modifier = Modifier.padding(contentPadding)
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // UI components using Jetpack Compose
            // Example: TextFields, Buttons, etc.
            OutlinedTextField(
                value = email,
                onValueChange = { email = it },
                label = { Text("Email") }
            )
            OutlinedTextField(
                value = password,
                onValueChange = { password = it },
                label = { Text("Password") }
            )
            OutlinedTextField(
                value = username,
                onValueChange = { username = it },
                label = { Text("Username") }
            )
            Button(
                onClick = { authService.loginUser(
                    email, password,
                    onSuccess = { message: String ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                            navController.navigate(MAIN_SCREEN)
                            authService.saveUser(email, password)
                        }
                    },
                    onFailure = { message: String ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                ) }
            ) {
                Text("Login")
            }
            Button(
                onClick = { authService.registerUser(email, password, username,
                    onSuccess = { message: String ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                            navController.navigate(MAIN_SCREEN)
                            authService.saveUser(email, password)
                        }
                    },
                    onFailure = { message: String ->
                        scope.launch {
                            snackbarHostState.showSnackbar(message)
                        }
                    }
                ) }
            ) {
                Text("Register")
            }
        }
    }
}