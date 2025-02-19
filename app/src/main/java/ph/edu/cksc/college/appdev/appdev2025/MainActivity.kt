package ph.edu.cksc.college.appdev.appdev2025

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import ph.edu.cksc.college.appdev.appdev2025.data.DiaryEntry
import ph.edu.cksc.college.appdev.appdev2025.data.SampleDiaryEntries
import ph.edu.cksc.college.appdev.appdev2025.screens.AboutScreen
import ph.edu.cksc.college.appdev.appdev2025.screens.DiaryEntryScreen
import ph.edu.cksc.college.appdev.appdev2025.screens.DiaryEntryView
import ph.edu.cksc.college.appdev.appdev2025.screens.MainScreen
import ph.edu.cksc.college.appdev.appdev2025.ui.theme.AppDev2025Theme
import java.time.LocalDateTime

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDev2025Theme {
                // Simple Navigation patterned after
                // https://saurabhjadhavblogs.com/ultimate-guide-to-jetpack-compose-navigation
                AppNavigation()
            }
        }
    }

    @SuppressLint("UnrememberedMutableState")
    @Composable
    fun AppNavigation() {
        val navController = rememberNavController()
        val viewModel = object: DiaryEntryView {
            @SuppressLint("UnrememberedMutableState")
            override var diaryEntry = mutableStateOf(DiaryEntry())
            init {
                diaryEntry.value = DiaryEntry(
                    "",
                    0,
                    "Lexi",
                    "Test...Test...Test...",
                    LocalDateTime.of(2024, 1, 1, 7, 30).toString()
                )
            }
            override var modified: Boolean = false

            override fun onTitleChange(newValue: String) {
                diaryEntry.value = diaryEntry.value.copy(title = newValue)
                modified = true
            }
            override fun onContentChange(newValue: String) {
                diaryEntry.value = diaryEntry.value.copy(content = newValue)
                modified = true
            }
            override fun onMoodChange(newValue: Int) {
                diaryEntry.value = diaryEntry.value.copy(mood = newValue)
                modified = true
            }
            override fun onDateTimeChange(newValue: LocalDateTime) {
                val newDueDate = newValue.toString()
                diaryEntry.value = diaryEntry.value.copy(dateTime = newDueDate)
                modified = true
            }
            override fun onDoneClick(popUpScreen: () -> Unit) {
                if (diaryEntry.value.id == "") {
                    SampleDiaryEntries.entries.add(diaryEntry.value)
                } else {
                    var index = 0
                    for (entry in SampleDiaryEntries.entries) {
                        if (entry.id == diaryEntry.value.id) {
                            break
                        }
                        index++
                    }
                    SampleDiaryEntries.entries[index] = diaryEntry.value
                }
                navController.popBackStack()
            }
        }
        NavHost(navController = navController, startDestination = MAIN_SCREEN) {
            composable(MAIN_SCREEN) { MainScreen(navController) }
            composable(ABOUT_SCREEN) { AboutScreen(navController) }
            composable("$DIARY_ENTRY_SCREEN/{id}",
                arguments = listOf(navArgument("id") { type = NavType.StringType })
            ) { backStackEntry ->
                val arguments = requireNotNull(backStackEntry.arguments)
                val id = arguments.getString("id") ?: "1"
                Log.d("Test", "id: " + id)
                for (entry in SampleDiaryEntries.entries) {
                    if (entry.id == id) {
                        viewModel.diaryEntry = mutableStateOf(entry)
                        break
                    }
                }
                //viewModel.diaryEntry
                //val viewModel: DiaryEntryViewModel = hiltViewModel()
                DiaryEntryScreen(navController = navController, viewModel = viewModel)
            }
        }
    }
}
