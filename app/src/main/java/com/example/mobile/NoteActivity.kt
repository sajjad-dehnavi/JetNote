package com.example.mobile

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.core.view.WindowCompat
import androidx.navigation.compose.rememberNavController
import com.example.datastore.DataStore
import com.example.graph.Graph
import com.example.mobile.CONS.AUDIOS
import com.example.mobile.CONS.IMAGES
import com.example.note.NoteVM
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import dagger.hilt.android.AndroidEntryPoint
import java.io.File
import java.util.*

@AndroidEntryPoint
class NoteActivity : ComponentActivity() {

    val vm = viewModels<NoteVM>()

    @SuppressLint("CoroutineCreationDuringComposition")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        WindowCompat.setDecorFitsSystemWindows(window,false)

        setContent {
            val navHostController = rememberNavController()
            val scope = rememberCoroutineScope()

            intentHandler(intent, this@NoteActivity, navHostController, scope)

            AppTheme {
                Graph(navHostController)
            }
        }

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.clear()
    }

    @Composable
    private fun AppTheme(content: @Composable () -> Unit) {
        val currentTheme = DataStore(LocalContext.current).isDarkTheme.collectAsState(false).value
        val systemUiController = rememberSystemUiController()
        val isDarkUi = isSystemInDarkTheme()

        val theme = when {
            isSystemInDarkTheme() -> darkColorScheme()
            currentTheme -> darkColorScheme()
            else -> lightColorScheme()
        }

        SideEffect {
            systemUiController.apply {
                setStatusBarColor(Color.Transparent, !isDarkUi)
                setNavigationBarColor(
                    if (currentTheme || isDarkUi) Color(red = 28, green = 27, blue = 31)
                    else Color(red = 255, green = 251, blue = 254)
                )
            }
        }

        MaterialTheme(colorScheme = theme, content = content)
    }
    @Deprecated("Deprecated in Java",
        ReplaceWith("super.onBackPressed()", "androidx.activity.ComponentActivity")
    )
    override fun onBackPressed() {
        super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        urlPreview(this,null,null,null,null,null,null)?.cleanUp()
    }

    override fun onStart() {
        super.onStart()
        // TODO: move it to init module as work manager.
//        File(this.filesDir.path + "/" + IMAGES).mkdirs()
        File(this.cacheDir.path + File.pathSeparator + IMAGES).mkdirs()
//        File(this.filesDir.path + "/" + AUDIOS).mkdirs()
        File(this.cacheDir.path + File.pathSeparator + AUDIOS).mkdirs()
//        Toast.makeText(this, "files created!", Toast.LENGTH_SHORT).show()

//        mapOf(
//            "Coffee" to "Prepare hot coffee for my self.",
//            "Certification" to "Call instructor for complete details.",
//            "Team Meeting" to "Planning sprint log for next product application update.",
//            "Birthday Party" to "Tomorrow is my brother birthday there will be party at 7:00 pm.",
//            "Vacation Tickets" to  "Buy tickets for the family vacation.",
//            "Appointment" to "Health check up with physician."
//
//        ).forEach {
//            vm.value.addNote(
//                Note(
//                    uid = UUID.randomUUID().toString(),
//                    title = it.key,
//                    description = it.value
//                )
//            )
//        }
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
    }

    override fun onResume() {
        super.onResume()
        checkShortcut(this)
    }

}

