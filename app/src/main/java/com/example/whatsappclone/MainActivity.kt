package com.example.whatsappclone

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.whatsappclone.navigation.WhatsappNavigation
import com.example.whatsappclone.ui.theme.WhatsappCloneTheme
import com.example.whatsappclone.welcomeScreens.HomeScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WhatsappCloneTheme {
                WhatsappNavigation()
            }
        }
    }
}
