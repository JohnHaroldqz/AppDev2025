package ph.edu.cksc.college.appdev.appdev2025.screens

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.AccessTime
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import ph.edu.cksc.college.appdev.appdev2025.data.moodList

import ph.edu.cksc.college.appdev.appdev2025.dialog.DateDialog
import ph.edu.cksc.college.appdev.appdev2025.dialog.TimeDialog
import ph.edu.cksc.college.appdev.appdev2025.data.DiaryEntry
import ph.edu.cksc.college.appdev.appdev2025.ui.theme.AppDev2025Theme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEntryScreen(
    viewModel: DiaryEntryView,
    navController: NavHostController
) {
    val entry by viewModel.diaryEntry
    val activity = LocalContext.current
    val date: LocalDateTime = LocalDateTime.parse(entry.dateTime)

    var showDatePicker by remember { mutableStateOf(false) }
    DateDialog(
        showDatePicker = showDatePicker, onShowDatePickerChange = { showDatePicker = it},
        date = date, onDateChange = { viewModel.onDateTimeChange(it) }
    )

    var showTimePicker by remember { mutableStateOf(false) }
    TimeDialog(
        showTimePicker = showTimePicker, onShowTimePickerChange = { showTimePicker = it},
        date = date, onDateChange = { viewModel.onDateTimeChange(it) }
    )

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(
                        if (entry.id.isEmpty())
                            "Add Diary Entry"
                        else
                            "Edit Diary Entry"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = {
                        // check if modified...
                        if (viewModel.modified) {
                            viewModel.onDoneClick {
                                Toast.makeText(
                                    activity,
                                    if (entry.id.isEmpty()) "New Entry" else "Entry updated: ${entry.id}",
                                    Toast.LENGTH_SHORT
                                ).show()
                                navController.popBackStack()
                            }
                        } else {
                            navController.popBackStack()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                },
                actions = {
                    IconButton(onClick = {
                        showDatePicker = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.DateRange,
                            contentDescription = "Date",
                        )
                    }
                    IconButton(onClick = {
                        showTimePicker = true
                    }) {
                        Icon(
                            imageVector = Icons.Filled.AccessTime,
                            contentDescription = "Time"
                        )
                    }
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier.padding(innerPadding)
        ) {
            DiaryEntry(
                viewModel = viewModel
            )
        }
    }
    val openDialog = remember { mutableStateOf(false)  }
    BackHandler(
        enabled = true
    ) {
        if (viewModel.modified) {
            openDialog.value = true
        } else {
            navController.popBackStack()
        }
    }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = {
                openDialog.value = false
            },
            title = {
                Text(text = "Diary Entry")
            },
            text = {
                Text("Discard changes?")
            },
            confirmButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                        navController.popBackStack()
                    }) {
                    Text("Yes")
                }
            },
            dismissButton = {
                Button(
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text("No")
                }
            }
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DiaryEntry(
    viewModel: DiaryEntryView
) {
    val entry by viewModel.diaryEntry
    var expanded by remember { mutableStateOf(false) }
    val formatter = DateTimeFormatter.ofPattern("MMM d, yy\nh:mm a")
    val date = LocalDateTime.parse(entry.dateTime)

    Column(
        modifier = Modifier.fillMaxSize(),
    ) {
        Row {
            ExposedDropdownMenuBox(
                expanded = expanded,
                onExpandedChange = {
                    expanded = !expanded
                }
            ) {
                OutlinedTextField(
                    value = moodList[entry.mood].mood,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
                    modifier = Modifier.menuAnchor(),
                    leadingIcon = {
                        Icon(
                            imageVector = moodList[entry.mood].icon,
                            tint = moodList[entry.mood].color,
                            contentDescription = moodList[entry.mood].mood
                        )
                    },
                    label = { Text("Mood") }
                )
                ExposedDropdownMenu(
                    expanded = expanded,
                    onDismissRequest = { expanded = false }
                ) {
                    moodList.forEachIndexed { index, item ->
                        DropdownMenuItem(
                            text = {
                                Row() {
                                    Icon(
                                        imageVector = item.icon,
                                        tint = item.color,
                                        contentDescription = item.mood
                                    )
                                    Text(
                                        text = item.mood,
                                        modifier = Modifier.padding(start = 16.dp)
                                    )
                                }
                            },
                            onClick = {
                                viewModel.onMoodChange(index)
                                expanded = false
                            }
                        )
                    }
                }
            }
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
                text = formatter.format(date)
            )
        }
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            value = entry.title,
            onValueChange = {
                viewModel.onTitleChange(it)
            },
            label = { Text("Title") }
        )
        OutlinedTextField(
            modifier = Modifier.fillMaxSize(),
            value = entry.content,
            onValueChange = {
                viewModel.onContentChange(it)
            },
            label = { Text("Content") }
        )
    }
}

@Preview(showBackground = true)
@Composable
fun EditDiaryPreview() {
    val navController = rememberNavController()
    AppDev2025Theme(dynamicColor = false) {
        DiaryEntryScreen(
            viewModel = object : DiaryEntryView {
                @SuppressLint("UnrememberedMutableState")
                override val diaryEntry = mutableStateOf(DiaryEntry())

                init {
                    diaryEntry.value = DiaryEntry(
                        "merong-id",
                        0,
                        "Lexi",
                        "Test...Test...Test...",
                        LocalDateTime.of(2024, 1, 1, 7, 30).toString()
                    )
                }

                override var modified: Boolean = true

                override fun onTitleChange(newValue: String) {
                    diaryEntry.value = diaryEntry.value.copy(title = newValue)
                }

                override fun onContentChange(newValue: String) {
                    diaryEntry.value = diaryEntry.value.copy(content = newValue)
                }

                override fun onMoodChange(newValue: Int) {
                    diaryEntry.value = diaryEntry.value.copy(mood = newValue)
                }

                override fun onDateTimeChange(newValue: LocalDateTime) {
                    val newDueDate = newValue.toString()
                    diaryEntry.value = diaryEntry.value.copy(dateTime = newDueDate)
                }

                override fun onDoneClick(popUpScreen: () -> Unit) {

                }
            },
            navController = navController
        )
    }
}