package com.example.whatsappclone.navScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.R
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Todo
import com.example.whatsappclone.uiComponents.RectInfoDesign
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Chats(modifier: Modifier, userList: List<UserProfile>, nav:(UserProfile)->Unit){
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val list = userList.distinctBy{ it.uid}
    var userProfile: UserProfile
    LazyColumn(modifier.fillMaxSize()){
        item {
            list.forEach { user->
                if(uid==user.uid){
                    userProfile=user
                    Row(modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                        verticalAlignment = Alignment.CenterVertically){
                        AsyncImage(user.url,null,
                            modifier = Modifier
                                .size(70.dp)
                                .clip(CircleShape)
                                .clickable(onClick = {
                                    nav(userProfile)
                                }),
                            contentScale = ContentScale.Crop)
                        Spacer(Modifier.width(8.dp))
                        Column(modifier = Modifier.weight(1f)){
                            Text(user.name+"(You)", fontWeight = FontWeight.Bold)
                                Text("Message", color = Color.DarkGray)
                        }
                        Text("2:30 PM", fontWeight = FontWeight.Bold, color = Color.LightGray)
                    }
                }
            }
        }
        items(userList){user->
            RectInfoDesign(user,false,nav)
            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}