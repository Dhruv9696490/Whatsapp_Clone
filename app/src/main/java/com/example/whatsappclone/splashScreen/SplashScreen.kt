package com.example.whatsappclone.splashScreen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.navigation.Routes
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(navController: NavHostController){
    LaunchedEffect(Unit){
        delay(1500)
        navController.navigate(Routes.Welcome.toString()){
            popUpTo(Routes.Splash.toString()){inclusive=true}
        }
    }
    Box(modifier = Modifier.fillMaxSize().padding(32.dp)){
        Image( painter = painterResource(id = R.drawable.whatsapp_icon),null,
            modifier = Modifier.size(100.dp).align(alignment = Alignment.Center))
        Column(modifier = Modifier.align(alignment = Alignment.BottomCenter),
            horizontalAlignment = Alignment.CenterHorizontally){
            Text("From", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                color = Color.DarkGray)
            Row {
                Icon(painter = painterResource(id = R.drawable.meta), contentDescription = null,
                    modifier = Modifier.size(28.dp),
                    tint = colorResource(id = R.color.light_green)
                )
                Text(" Meta", fontSize = 24.sp, fontWeight = FontWeight.Bold,
                    color = Color.DarkGray)
            }
        }
    }
}

