package com.example.whatsappclone.NavScreen

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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material3.HorizontalDivider
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
fun Update(modifier: Modifier, userList: List<UserProfile>,nav:(UserProfile)-> Unit){
    val context = LocalContext.current
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    val list = userList.distinctBy { it.uid }
    Column(modifier.fillMaxSize()){
        Text(" Status", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        LazyColumn(Modifier.wrapContentSize()){
            item{
                list.forEach {user->
                    if(user.uid==uid){
                        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
                            verticalAlignment = Alignment.CenterVertically){
                            Box{
                                AsyncImage(user.url,null,
                                    modifier = Modifier.size(70.dp).clip(CircleShape),
                                    contentScale = ContentScale.Crop)
                                IconButton(onClick = {
                                    Todo.showToast(context,"Pending")
                                },
                                    modifier = Modifier.size(40.dp).align(alignment = Alignment.BottomEnd)){
                                    Icon(Icons.Filled.AddCircle,null, tint = colorResource(id = R.color.light_green),
                                        modifier = Modifier.size(24.dp))
                                }
                            }
                            Spacer(Modifier.width(8.dp))
                            Column(modifier = Modifier.weight(1f)){
                                Text("My Status", fontWeight = FontWeight.Bold, fontSize = 22.sp)
                                Text("Tap to add updates", fontWeight = FontWeight.Bold, color = Color.Gray, fontSize = 16.sp)
                            }
                        }
                    }
                }
            }
            items(userList.subList(4,8)){item->
                RectInfoDesign(item, exp = true, nav)
                Spacer(modifier = Modifier.height(4.dp))
            }
        }
        HorizontalDivider()
        Spacer(modifier = Modifier.height(8.dp))
        Text(" Channel", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        Spacer(modifier = Modifier.height(8.dp))
        Text("stay updated on topics that matter to you. Find channel to follow below",
            modifier = Modifier.padding(horizontal = 8.dp))
    }
}