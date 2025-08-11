package com.example.whatsappclone.NavScreen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.R
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Todo
import com.example.whatsappclone.uiComponents.RecentCalls

@Composable
fun Calls(modifier: Modifier, userList: List<UserProfile>) {
    val context = LocalContext.current
    Column(modifier.fillMaxSize()){
        Text(" Favorite", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyRow(modifier = Modifier.fillMaxWidth()){
            items(userList.subList(2,8)){user->
                    Column(modifier = Modifier.padding(8.dp),verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally){
                        AsyncImage(user.url,null,
                            modifier = Modifier.size(70.dp).clip(CircleShape),
                            contentScale = ContentScale.Crop)
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(user.name, fontWeight = FontWeight.Bold)
                }

            }
        }
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { Todo.showToast(context,"pending")},
            modifier = Modifier.fillMaxWidth().height(50.dp).padding(horizontal = 8.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_green))){
            Text("Start a new call", fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text(" Recent Calls", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(modifier = Modifier.wrapContentSize()){
            items(userList.subList(1,14)){user->
                RecentCalls(user)
                Spacer(modifier = Modifier.height(8.dp))
            }
        }

    }
}