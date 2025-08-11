package com.example.whatsappclone.navigation

import android.content.Context
import android.net.Uri
import android.widget.Toast
import com.example.whatsappclone.data.UserProfile
import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString


sealed class Routes{
    data object Splash : Routes()
    data object Welcome : Routes()
    data object Registration : Routes()
    data object Login : Routes()
    data object Sign : Routes()
    data object Home : Routes()
    data object Chat : Routes()
    data object DpDialogue : Routes()
    data object Setting : Routes()
    data object Profile : Routes()
}
object Todo{
    fun showToast(context: Context,message: String){
        Toast.makeText(context,message, Toast.LENGTH_SHORT).show()
    }
}
