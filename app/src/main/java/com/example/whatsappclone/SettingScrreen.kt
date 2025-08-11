package com.example.whatsappclone

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Build
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.MailOutline
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.data.SettingList
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Routes
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore

@Composable
fun SettingScreen(navHostController: NavHostController){
    var userProfile: UserProfile? by remember { mutableStateOf(UserProfile()) }
    LaunchedEffect(Unit){
       Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!)
           .get().addOnCompleteListener{it->
                if(it.isSuccessful){
                    userProfile=it.result.toObject(UserProfile::class.java)
                }
            }
    }
    val settingList = listOf<SettingList>(
        SettingList(
            image = Icons.Default.AccountCircle,
            name = "Account",
            description = "Security notification, log out"),
        SettingList(
            image = Icons.Default.Lock,
            name = "Privacy",
            description = "Block contacts, disappearing messages"),
        SettingList(
            image = Icons.AutoMirrored.Filled.List,
            name = "Lists",
            description = "Manage people and groups"),
        SettingList(
            image = Icons.Default.MailOutline,
            name = "Chats",
            description = "Theme, wallpapers, chat history"),
        SettingList(
            image = Icons.Default.Notifications,
            name = "Notifications",
            description = "Message, group & call tones"),
        SettingList(
            image = Icons.Default.Build,
            name = "Storage and data",
            description = "Network usage, auto-download"),
        SettingList(
            image = Icons.Default.Person,
            name = "Accessibility",
            description = "Increase contrast, animation"),
        SettingList(
            image = Icons.Default.Info,
            name = "Help",
            description = "Help center, contact us, privacy policy")
    )
    Column(modifier = Modifier.fillMaxSize().padding(top = 40.dp, bottom = 8.dp)){
        Row(modifier = Modifier.padding(horizontal = 8.dp)
            ,verticalAlignment = Alignment.CenterVertically){
            IconButton(onClick = {
                navHostController.popBackStack()
            },
                modifier = Modifier.size(32.dp)){
                Icon(Icons.AutoMirrored.Filled.ArrowBack,null,
                    modifier = Modifier.size(28.dp))
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(" Settings", fontSize = 24.sp, fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.weight(1f))
            IconButton(onClick = {},
                modifier = Modifier.size(32.dp)){
                Icon(Icons.Default.Search,null,
                    modifier = Modifier.size(32.dp))
            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        HorizontalDivider(thickness = 2.dp, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp).clickable(
                onClick = {
                    navHostController.navigate(Routes.Profile.toString())
                }
            ),
            verticalAlignment = Alignment.CenterVertically){
            AsyncImage(userProfile?.url,null,
                modifier = Modifier
                    .size(70.dp)
                    .clip(CircleShape),
                contentScale = ContentScale.Crop)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)){
                userProfile?.name?.let { Text(it, fontWeight = FontWeight.Bold, fontSize = 24.sp) }
                Text("Hey there! I am using WhatsApp.", color = Color.DarkGray)
            }
        }
        LazyColumn(modifier = Modifier.fillMaxSize().padding(vertical = 0.dp)){
            items(settingList){item->
                Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Icon(item.image,null, modifier = Modifier.size(40.dp),
                        tint = Color.DarkGray)
                    Spacer(Modifier.width(16.dp))
                    Column {
                        Text(item.name, fontSize = 24.sp)
                        Spacer(Modifier.height(0.dp))
                        Text(item.description)
                    }
                }
            }
        }
    }
}