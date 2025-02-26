package ph.edu.cksc.college.appdev.appdev2025.screens

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ph.edu.cksc.college.appdev.appdev2025.ui.theme.AppDev2025Theme

data class RegistrationData(
    val userName: String = "",
    val email: String = "",
    val password: String = "",
    val retypePassword: String = "",
)

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RegisterScreen(navController: NavHostController) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text("Register")
                },
                navigationIcon = {
                    IconButton(onClick = {
                        navController.popBackStack()
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        RegisterScrollContent(innerPadding)
    }
}

@SuppressLint("UnrememberedMutableState")
@Composable
fun RegisterScrollContent(innerPadding: PaddingValues) {
    var data by mutableStateOf(RegistrationData())
    var password by rememberSaveable { mutableStateOf("") }
    Box(
        modifier = Modifier.padding(innerPadding)
    ) {
        Column() {
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = data.userName,
                onValueChange = {
                    data = data.copy(userName = it)
                },
                label = { Text("User name") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = data.email,
                onValueChange = {
                    data = data.copy(email = it)
                },
                label = { Text("Email") }
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = password,
                onValueChange = {
                    password = it
                },
                label = { Text("Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
            OutlinedTextField(
                modifier = Modifier.fillMaxWidth(),
                value = data.retypePassword,
                onValueChange = {
                    data = data.copy(retypePassword = it)
                },
                label = { Text("Retype Password") },
                visualTransformation = PasswordVisualTransformation(),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Password)
            )
        }
    }
}

@Preview()
@Composable
fun PreviewRegisterScreen() {
    val navController = rememberNavController()
    AppDev2025Theme {
        RegisterScreen(navController)
    }
}