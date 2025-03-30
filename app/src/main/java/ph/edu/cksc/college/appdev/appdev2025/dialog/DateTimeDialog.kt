package ph.edu.cksc.college.appdev.appdev2025.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateDialog(
    showDatePicker: Boolean, onShowDatePickerChange: (Boolean) -> Unit,
    date: LocalDateTime, onDateChange: (LocalDateTime) -> Unit,
) {
    if (showDatePicker) {
        val datePickerState = rememberDatePickerState(initialSelectedDateMillis = date.toInstant(
            ZoneOffset.UTC).toEpochMilli())
        DatePickerDialog(
            onDismissRequest = {
                onShowDatePickerChange(false)
            },
            confirmButton = {
                TextButton(onClick = {
                    onShowDatePickerChange(false)
                    val newDate = Instant.ofEpochMilli(datePickerState.selectedDateMillis!!).atZone(
                        ZoneId.systemDefault()).toLocalDateTime()
                    onDateChange(LocalDateTime.of(newDate.year, newDate.month, newDate.dayOfMonth, date.hour, date.minute))
                }) {
                    Text(text = "Confirm")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    onShowDatePickerChange(false)
                }) {
                    Text(text = "Cancel")
                }
            }
        ) {
            DatePicker(
                state = datePickerState
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimeDialog(
    showTimePicker: Boolean, onShowTimePickerChange: (Boolean) -> Unit,
    date: LocalDateTime, onDateChange: (LocalDateTime) -> Unit,
) {
    if (showTimePicker) {
        val timePickerState = rememberTimePickerState(
            initialHour = date.hour,
            initialMinute = date.minute
        )
        BasicAlertDialog(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = MaterialTheme.colorScheme.surface,
                    shape = RoundedCornerShape(size = 12.dp)
                ),
            onDismissRequest = { onShowTimePickerChange(false) }
        ) {
            Column(
                modifier = Modifier
                    .background(
                        color = Color.LightGray.copy(alpha = 0.3f)
                    )
                    .padding(top = 28.dp, start = 20.dp, end = 20.dp, bottom = 12.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                // time picker
                TimePicker(state = timePickerState)

                // buttons
                Row(
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    // dismiss button
                    TextButton(onClick = { onShowTimePickerChange(false) }) {
                        Text(text = "Dismiss")
                    }

                    // confirm button
                    TextButton(
                        onClick = {
                            onShowTimePickerChange(false)
                            onDateChange(LocalDateTime.of(date.year, date.month, date.dayOfMonth, timePickerState.hour, timePickerState.minute))
                        }
                    ) {
                        Text(text = "Confirm")
                    }
                }
            }
        }
    }
}
