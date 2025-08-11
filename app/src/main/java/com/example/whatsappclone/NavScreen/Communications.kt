package com.example.whatsappclone.NavScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Todo

@Composable
fun Communication(modifier: Modifier, userList: List<UserProfile>) {
    val context=LocalContext.current
    Column(modifier.fillMaxSize().padding(8.dp)){
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = { Todo.showToast(context,"pending")},
            modifier = Modifier.fillMaxWidth().height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_green))){
            Text("Start a new community", fontWeight = FontWeight.Bold,
                fontSize = 20.sp)
        }
        Spacer(modifier = Modifier.height(16.dp))
        Text("Your Communities", fontSize = 24.sp, fontWeight = FontWeight.SemiBold)
        Spacer(modifier = Modifier.height(8.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(id = R.drawable.img),null,
                modifier = Modifier.size( 60.dp).clip(CircleShape),
                contentScale = ContentScale.Crop)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)){
                Text("Android Jetpack", fontWeight = FontWeight.Bold)
                Text("250,000 members", fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(id = R.drawable.img),null,
                modifier = Modifier.size( 60.dp).clip(CircleShape),
                contentScale = ContentScale.Crop)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)){
                Text("Flutter Jobs", fontWeight = FontWeight.Bold)
                Text("310,247 members", fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            Image(painter = painterResource(id = R.drawable.img),null,
                modifier = Modifier.size( 60.dp).clip(CircleShape),
                contentScale = ContentScale.Crop)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)){
                Text("Artificial Intelligence ", fontWeight = FontWeight.Bold)
                Text("453,890", fontWeight = FontWeight.Bold, color = Color.Gray)
            }
        }
    }
}