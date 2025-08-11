package com.example.whatsappclone.uiComponents

import androidx.compose.foundation.Image
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Call
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.R
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Todo

@Composable
fun DialogOfDP(user: UserProfile,navController: NavHostController){
    val context = LocalContext.current
    Dialog(onDismissRequest = {
        navController.popBackStack()
    },
        content = {
            Card(colors = CardDefaults.cardColors(containerColor = Color.DarkGray),
                shape = RoundedCornerShape(40.dp)
            ){
                Column(modifier = Modifier.wrapContentSize().padding(8.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally){
                    AsyncImage(user.url,null,
                        modifier = Modifier.size( 320.dp).clip(CircleShape),
                        contentScale = ContentScale.Crop)
                    Spacer(Modifier.height(0.dp))
                    Row(modifier = Modifier.fillMaxWidth().padding(8.dp),verticalAlignment = Alignment.CenterVertically){
                        Text(user.name, fontSize = 32.sp, fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.weight(1f).basicMarquee(velocity = 50.dp),
                            overflow = TextOverflow.Visible,
                            maxLines = 1)
                        Row(verticalAlignment = Alignment.CenterVertically){
                            IconButton(onClick = {
                                Todo.showToast(context,"pending")

                            }){
                                Image(painterResource(id = R.drawable.chat_icon),null,
                                    Modifier.size(30.dp),
                                    colorFilter = ColorFilter.tint(Color.White))
                            }
                            Spacer(Modifier.width(4.dp))
                            IconButton(onClick = {  Todo.showToast(context,"pending")}){
                                Icon(Icons.Default.Call,null,
                                    Modifier.size(32.dp),
                                    tint = Color.White)
                            }
                        }

                    }

                }
            }

        })
}