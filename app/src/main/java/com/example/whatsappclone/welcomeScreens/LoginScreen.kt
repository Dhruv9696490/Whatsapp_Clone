package com.example.whatsappclone.welcomeScreens

import android.R.attr.enabled
import android.R.attr.onClick
import android.R.attr.password
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.whatsappclone.R
import com.example.whatsappclone.Utils
import com.example.whatsappclone.navigation.Routes
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(navController: NavController){
    var email by remember { mutableStateOf("") }
    var password by remember {mutableStateOf("")}
    val context = LocalContext.current
    val viewModel: ViewModelWhatsApp= viewModel()
    var eyeOpener by remember { mutableStateOf(false) }
    LaunchedEffect(viewModel.whatsAppState.value){
        when(viewModel.whatsAppState.value){
            is WhatsAppState.Authenticated -> navController.navigate(Routes.Home.toString())
            is WhatsAppState.Error -> Toast.makeText(context,viewModel.whatsAppState.value.toString(), Toast.LENGTH_SHORT).show()
            else -> null
        }
    }
    Box(modifier = Modifier.fillMaxSize()){
        AsyncImage(model = Utils.theme,null,
            modifier = Modifier.fillMaxSize().clip(RectangleShape),
            contentScale = ContentScale.Crop)
            Column(Modifier.wrapContentSize().padding(16.dp).align(alignment = Alignment.Center),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally){
                Text("Welcome back!",
                    style = TextStyle(
                        fontSize = 30.sp,
                        fontFamily = FontFamily.Monospace,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    ),
                    color = colorResource(id = R.color.light_green),
                    modifier = Modifier.background(Color.White).border(1.dp,Color.DarkGray,CircleShape).padding(4.dp)
                    )
                Spacer(modifier = Modifier.height(4.dp))
                Text("Login to you account",
                    style = TextStyle(
                        fontSize = 20.sp,
                        fontFamily = FontFamily.Serif,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center,
                        color = colorResource(id = R.color.dark_green)
                    ),
                    modifier = Modifier.background(Color.White).border(1.dp,Color.DarkGray,CircleShape).padding(4.dp)
                    )
                Spacer(modifier = Modifier.height(8.dp))
                OutlinedTextField(value = email,
                    onValueChange = {email=it},
                    label = {Text("Email")},
                    placeholder = {Text("Enter your email")},
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    shape = CircleShape)
                Spacer(modifier = Modifier.height(4.dp))
                OutlinedTextField(value = password,
                    onValueChange = {password=it},
                    label = {Text("Password")},
                    placeholder = {Text("Enter your password")},
                    modifier = Modifier.fillMaxWidth().background(Color.White),
                    shape = CircleShape,

                    visualTransformation = if(!eyeOpener) PasswordVisualTransformation() else VisualTransformation.None,
                    trailingIcon = {
                        IconButton(onClick = {eyeOpener = !eyeOpener},
                            modifier = Modifier.size(50.dp))
                            {
                            Icon(painterResource(R.drawable.eye),null,
                                tint = Color.Unspecified,
                                modifier = Modifier.size(40.dp))
                        }
                    })
                Spacer(modifier = Modifier.height(4.dp))
                Button(onClick = {
                    if(email.isNotEmpty() && password.isNotEmpty()){
                        viewModel.login(email,password)
                    }
                },
                    enabled = viewModel.whatsAppState.value!= WhatsAppState.Loading,
                    colors = ButtonDefaults.buttonColors(containerColor = colorResource(id = R.color.light_green)),
                    modifier = Modifier.fillMaxWidth().height(60.dp).background(Color.White).border(2.dp,Color.DarkGray,CircleShape).padding(4.dp)
                ){
                    Text("Login", fontSize = 22.sp, fontWeight = FontWeight.SemiBold, fontFamily = FontFamily.Default)
                }
                Spacer(modifier = Modifier.height(8.dp))
                TextButton(onClick = {
                    navController.navigate(Routes.Sign.toString())
                },
                    modifier = Modifier.background(Color.White).border(1.dp,Color.DarkGray,CircleShape).padding(0.dp)){
                    Text("Don't have account, SignIn!", color = colorResource(id = R.color.dark_green))
                }
            }
        }
    }
