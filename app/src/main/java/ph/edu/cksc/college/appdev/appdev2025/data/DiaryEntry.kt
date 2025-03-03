package ph.edu.cksc.college.appdev.appdev2025.data

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Block
import androidx.compose.material.icons.filled.Cloud
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Hotel
import androidx.compose.material.icons.filled.MoodBad
import androidx.compose.material.icons.filled.RamenDining
import androidx.compose.material.icons.filled.SentimentDissatisfied
import androidx.compose.material.icons.filled.SentimentSatisfied
import androidx.compose.material.icons.filled.SentimentVeryDissatisfied
import androidx.compose.material.icons.filled.SentimentVerySatisfied
import androidx.compose.material.icons.filled.Star
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import java.time.LocalDateTime

data class DiaryEntry(
    val id: String = "",
    val mood: Int = 0,
    val title: String = "",
    val content: String = "",
    val dateTime: String =  LocalDateTime.now().toString(),
    val userId: String = "",
    val star: Int = 1

)

data class Mood(
    val mood: String,
    val icon: ImageVector,
    val color: Color
)

val moodList = listOf(
    Mood("Happy", Icons.Filled.SentimentSatisfied, Color(0xffd4a302)),
    Mood("Excited", Icons.Filled.SentimentVerySatisfied, Color(0xff109900)),
    Mood("Love", Icons.Filled.Favorite, Color(0xffee0000)),
    Mood("Hungry", Icons.Filled.RamenDining, Color(0xfffc7b03)),
    Mood("Angry", Icons.Filled.SentimentDissatisfied, Color(0xffff0000)),
    Mood("Furious", Icons.Filled.SentimentVeryDissatisfied, Color(0xffee00ee)),
    Mood("Sleepy", Icons.Filled.Hotel, Color(0xff0468bf)),
    Mood("Sad", Icons.Filled.MoodBad, Color(0xff5a5ae8)),
    Mood("Gloomy", Icons.Filled.Cloud, Color(0xff888888)),
    Mood("Block", Icons.Filled.Block, Color(0xffdd0000)),
    Mood("Star", Icons.Filled.Star, Color(0xffd4a302)),
)

object SampleDiaryEntries {
    // Sample conversation data
    val entries = mutableListOf(
        DiaryEntry(
            "1", 0,
            "Lexi",
            "Test...Test...Test...",
            LocalDateTime.of(2024, 1, 1, 7, 30).toString(), star = 1
        ),
        DiaryEntry(
            "2", 5,
            "Lexi",
            """List of Android versions:
            |Android KitKat (API 19)
            |Android Lollipop (API 21)
            |Android Marshmallow (API 23)
            |Android Nougat (API 24)
            |Android Oreo (API 26)
            |Android Pie (API 28)
            |Android 10 (API 29)
            |Android 11 (API 30)
            |Android 12 (API 31)""".trim(),
            LocalDateTime.of(2024, 1, 2, 8, 30).toString(), star = 2

        ),
        DiaryEntry(
            "3", 1,
            "Lexi",
            """I think Kotlin is my favorite programming language.
            |It's so much fun!""".trim(),
            LocalDateTime.of(2024, 1, 3, 9, 50).toString(),star = 3

        ),
        DiaryEntry(
            "4", 2,
            "Lex Luthor",
            "Searching for alternatives to XML layouts...",
            LocalDateTime.of(2024, 1, 5, 7, 30).toString(),star = 4
        ),
        DiaryEntry(
            "5", 3,
            "Lexi",
            """Hey, take a look at Jetpack Compose, it's great!
            |It's the Android's modern toolkit for building native UI.
            |It simplifies and accelerates UI development on Android.
            |Less code, powerful tools, and intuitive Kotlin APIs :)""".trim(),
            LocalDateTime.of(2024, 1, 7, 17, 30).toString(),star = 5

        ),
        DiaryEntry(
            "6", 8,
            "Lexi",
            "It's available from API 21+ :)",
            LocalDateTime.of(2024, 1, 8, 7, 30).toString(),star = 6

        ),
        DiaryEntry(
            "7", 2,
            "Lexi",
            "Writing Kotlin for UI seems so natural, Compose where have you been all my life?",
            LocalDateTime.of(2024, 1, 9, 7, 30).toString(),star = 7
        ),
        DiaryEntry(
            "8", 6,
            "Lexi",
            "Android Studio next version's name is Arctic Fox",
            LocalDateTime.of(2024, 1, 10, 7, 30).toString(),star = 8
        ),
        DiaryEntry(
            "8", 7,
            "Lexi",
            "Android Studio Arctic Fox tooling for Compose is top notch ^_^",
            LocalDateTime.of(2024, 2, 1, 7, 30).toString()
        ),
        DiaryEntry(
            "9", 4,
            "Lexi",
            "I didn't know you can now run the emulator directly from Android Studio",
            LocalDateTime.of(2024, 1, 2, 7, 30).toString()
        ),
        DiaryEntry(
            "7", 3,
            "Lexi",
            "Compose Previews are great to check quickly how a composable layout looks like",
            LocalDateTime.of(2024, 1, 3, 7, 30).toString()
        ),
        DiaryEntry(
            "3", 2,
            "Lexi",
            "Previews are also interactive after enabling the experimental setting",
            LocalDateTime.of(2024, 1, 4, 7, 30).toString()
        ),
        DiaryEntry(
            "1", 1,
            "Lexi",
            "Have you tried writing build.gradle with KTS?",
            LocalDateTime.of(2024, 1, 5, 7, 30).toString()
        ),
    )
}
