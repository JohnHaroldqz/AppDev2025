package ph.edu.cksc.college.appdev.appdev2025

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ph.edu.cksc.college.appdev.appdev2025.screens.AboutScreen
import ph.edu.cksc.college.appdev.appdev2025.screens.MainScreen
import ph.edu.cksc.college.appdev.appdev2025.ui.theme.AppDev2025Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDev2025Theme {
                AppNavigation()
            }
        }
    }
}

@Composable
fun AppNavigation() {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = "main") {
        composable("main") { MainScreen(navController) }
        composable("about") { AboutScreen(navController) }
        }
    }
