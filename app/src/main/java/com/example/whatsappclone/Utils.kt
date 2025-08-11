package com.example.whatsappclone

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

object Utils {
    val theme = "https://res.cloudinary.com/dvg3vkutf/image/upload/v1754821138/HD-wallpaper-real-madrid-background-pattern-sport-whatsapp-thumbnail_yevdka.jpg"
    val image= "https://res.cloudinary.com/dvg3vkutf/image/upload/v1754678207/a04d849cf591c2f980548b982f461401_idmgct.jpg"
    lateinit var navController: NavHostController
    fun getChatId(user1: String, user2: String): String {
        return listOf(user1, user2).sorted().joinToString("_")
    }
}