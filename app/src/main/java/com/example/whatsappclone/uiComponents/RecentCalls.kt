package com.example.whatsappclone.uiComponents

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Todo
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RecentCalls(userProfile: UserProfile){
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if(uid!=userProfile.uid){
    Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
        verticalAlignment = Alignment.CenterVertically) {
        AsyncImage(
            userProfile.url, null,
            modifier = Modifier.size(70.dp).clip(CircleShape),
            contentScale = ContentScale.Crop
        )
        Spacer(Modifier.width(8.dp))
        Column(modifier = Modifier.weight(1f)) {
            Text(userProfile.name, fontWeight = FontWeight.Bold)
            Text(
                "↶ Yesterday, " + "10:38" + " PM",
                fontWeight = FontWeight.Bold,
                fontFamily = FontFamily.SansSerif,
                color = Color.Gray
            )
        }
        IconButton(onClick = { Todo.showToast(context, "Pending") }) {
            Icon(
                Icons.Default.Call, null,
                Modifier.size(24.dp)
            )
        }
    }
    }
}