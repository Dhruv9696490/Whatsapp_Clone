package com.example.whatsappclone.data

import android.os.Parcelable
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import kotlinx.parcelize.Parcelize

data class NavData(
    val image: Painter,
    val name: String
)
data class DropDownData(
    val image: ImageVector,
    val name: String
)

data class SettingList(
    val image: ImageVector,
    val name: String,
    val description: String
)
@Parcelize
data class UserProfile(
    val links: String="",
    val about: String="",
    val uid: String="",
    val name: String="",
    val url: String=""
): Parcelable

data class Message(
    val senderId: String = "",
    val receiverId: String = "",
    val text: String = "",
    val timestamp: Long = System.currentTimeMillis()
)
