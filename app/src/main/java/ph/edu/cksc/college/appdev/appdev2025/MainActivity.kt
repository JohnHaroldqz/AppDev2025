package ph.edu.cksc.college.appdev.appdev2025

import android.content.res.Configuration
import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.annotation.RequiresApi
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import ph.edu.cksc.college.appdev.appdev2025.data.DiaryEntry
import ph.edu.cksc.college.appdev.appdev2025.data.SampleDiaryEntries
import ph.edu.cksc.college.appdev.appdev2025.data.moodList
import ph.edu.cksc.college.appdev.appdev2025.ui.theme.AppDev2025Theme
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppDev2025Theme {
                DiaryList(SampleDiaryEntries.entries)
            }
        }
    }
}

@Composable
fun DiaryList(messages: MutableList<DiaryEntry>) {
    LazyColumn {
        items(messages) { message ->
            DiaryEntryCard(message)
        }
    }
}

@Preview
@Composable
fun PreviewDiaryList() {
    AppDev2025Theme {
        DiaryList(SampleDiaryEntries.entries)
    }
}

@Composable
fun DiaryEntryCard(entry: DiaryEntry) {
    Row(modifier = Modifier.padding(all = 8.dp)) {
        IconButton(onClick = {
            //navController.navigate("$DIARY_ENTRY_SCREEN/${entry.id}")
        }) {
            Icon(
                imageVector = moodList[entry.mood].icon,
                tint = moodList[entry.mood].color,
                contentDescription = "About"
            )
        }
        Spacer(modifier = Modifier.width(8.dp))

        // We keep track if the message is expanded or not in this
        // variable
        var isExpanded by remember { mutableStateOf(false) }
        // surfaceColor will be updated gradually from one color to the other
        val surfaceColor by animateColorAsState(
            if (isExpanded) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.surface,
        )

        val formatter = DateTimeFormatter.ofPattern("EEEE MMMM d, yyyy h:mm a")
        val date = LocalDateTime.parse(entry.dateTime)

        // We toggle the isExpanded variable when we click on this Column
        Column(modifier = Modifier.clickable { isExpanded = !isExpanded }) {
            Text(
                text = entry.title,
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = formatter.format(date),
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.titleSmall
            )

            Spacer(modifier = Modifier.height(4.dp))

            Surface(
                shape = MaterialTheme.shapes.medium,
                shadowElevation = 1.dp,
                // surfaceColor color will be changing gradually from primary to surface
                color = surfaceColor,
                // animateContentSize will change the Surface size gradually
                modifier = Modifier.animateContentSize().padding(1.dp)
            ) {
                Text(
                    text = entry.content,
                    modifier = Modifier.padding(all = 4.dp),
                    // If the message is expanded, we display all its content
                    // otherwise we only display the first line
                    maxLines = if (isExpanded) Int.MAX_VALUE else 1,
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }
}

@Preview(name = "Light Mode", showBackground = true)
@Preview(
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    showBackground = true,
    name = "Dark Mode"
)
@Composable
fun PreviewDiaryEntryCard() {
    AppDev2025Theme {
        Surface(modifier = Modifier.fillMaxSize()) {
            DiaryEntryCard(
                entry = DiaryEntry(
                    "1", 0,
                    "Lexi",
                    "Test...Test...Test...",
                    LocalDateTime.of(2024, 1, 1, 7, 30).toString()
                )
            )
        }
    }
}
