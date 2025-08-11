package com.example.whatsappclone.uiComponents

import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RectInfoDesign(userProfile: UserProfile, exp: Boolean,nav:(UserProfile)->Unit){
    val uid = FirebaseAuth.getInstance().currentUser?.uid
    if(uid!=userProfile.uid){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
            verticalAlignment = Alignment.CenterVertically){
            AsyncImage(userProfile.url,null,
                modifier = Modifier
                    .size(if (!exp) 70.dp else 60.dp)
                    .clip(CircleShape)
                    .clickable(onClick = {
                        nav(userProfile)
                    }),
                contentScale = ContentScale.Crop)
            Spacer(Modifier.width(8.dp))
            Column(modifier = Modifier.weight(1f)){
                Text(userProfile.name, fontWeight = FontWeight.Bold)
                if (!exp){
                    Text(userProfile.message, color = Color.DarkGray)
                }else{
                    Text(userProfile.time, color = Color.DarkGray)
                }

            }
            if (!exp){
                Text(userProfile.time, fontWeight = FontWeight.Bold, color = Color.LightGray)
            }

        }
    }

}