package com.example.whatsappclone.welcomeScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.navigation.Routes

@Composable
fun WelcomeScreen(navController: NavHostController) {
    Column(modifier = Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.whatsapp_sticker),null,
            modifier = Modifier.size(300.dp))
        Spacer(modifier = Modifier.height(8.dp))
        Text("Welcome WhatsApp", fontSize = 40.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.SansSerif)
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = buildAnnotatedString {
            append("Read our ")
            withStyle(
                style = SpanStyle(
                    color = colorResource(R.color.light_green)
                )
            ){
                append("Private Policy ")
            }
            append("Tap 'Agree and continue' to accept the ")
            withStyle(
                style = SpanStyle(
                    color = colorResource(R.color.light_green)
                )
            ){
                append("Terms of Service")
            }
        },
            textAlign = TextAlign.Center,
            fontSize = 16.sp,
            fontFamily = FontFamily.SansSerif
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(onClick = {
            navController.navigate(Routes.Registration.toString()){
                popUpTo(Routes.Welcome.toString()){inclusive=true}
            }
        },
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_green)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 32.dp).height(55.dp)
        ){
            Text("Agree and Continue", color = Color.White, fontSize = 18.sp)
        }
    }
}