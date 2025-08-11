package com.example.whatsappclone.welcomeScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.whatsappclone.R
import com.example.whatsappclone.navigation.Routes

@Composable
fun RegistrationScreen(navController: NavHostController) {
    Column(Modifier.fillMaxSize().padding(vertical = 8.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally){
        Image(painter = painterResource(id = R.drawable.whatsapp_sticker),null,
            Modifier.size(500.dp,300.dp))
        Text("Welcome to WhatsApp",
            style = TextStyle(
                fontSize = 30.sp,
                fontFamily = FontFamily.Monospace,
                fontWeight = FontWeight.SemiBold,
                textAlign = TextAlign.Center
            ))
        Text("Start interaction with peoples",
            color = Color.DarkGray)
        Spacer(modifier= Modifier.height(8.dp))
        Button(onClick = {
            navController.navigate(Routes.Login.toString())
        },
            colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.dark_green)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(70.dp) ){
            Text("Login", textAlign = TextAlign.Center, fontSize = 20.sp)
        }
        Spacer(modifier= Modifier.height(8.dp))
        OutlinedButton(onClick = {
            navController.navigate(Routes.Sign.toString())
        },
            modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp).height(70.dp)){
            Text("SignUp", textAlign = TextAlign.Center, fontSize = 20.sp, color = colorResource(id = R.color.dark_green))
        }
    }
}