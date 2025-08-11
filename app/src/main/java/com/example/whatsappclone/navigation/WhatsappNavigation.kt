package com.example.whatsappclone.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost

import androidx.navigation.compose.composable
import androidx.navigation.compose.dialog
import androidx.navigation.compose.rememberNavController
import com.example.whatsappclone.ChatScreen
import com.example.whatsappclone.ProfileScreen
import com.example.whatsappclone.SettingScreen
import com.example.whatsappclone.Utils
import com.example.whatsappclone.data.UserProfile
import com.example.whatsappclone.splashScreen.SplashScreen
import com.example.whatsappclone.uiComponents.DialogOfDP
import com.example.whatsappclone.welcomeScreens.HomeScreen
import com.example.whatsappclone.welcomeScreens.LoginScreen
import com.example.whatsappclone.welcomeScreens.RegistrationScreen
import com.example.whatsappclone.welcomeScreens.SignInScreen
import com.example.whatsappclone.welcomeScreens.WelcomeScreen
import com.google.firebase.auth.FirebaseAuth


@Composable
fun WhatsappNavigation(){
    val navController = rememberNavController()

        val user = FirebaseAuth.getInstance().currentUser?.uid


    Utils.navController=navController
    NavHost(navController = navController, startDestination =  if(user==null)Routes.Splash.toString() else Routes.Home.toString() ){
        composable(Routes.Splash.toString()){
            SplashScreen(navController)
        }
        composable(Routes.Welcome.toString()){
            WelcomeScreen(navController)
        }
        composable(Routes.Registration.toString()){
            RegistrationScreen(navController)
        }
        composable(Routes.Login.toString()){
            LoginScreen(navController)
        }
        composable(Routes.Sign.toString()){
            SignInScreen(navController)
        }
        composable(Routes.Chat.toString()+"/{it}"){
            val id  = it.arguments?.getString("it") ?:""
                ChatScreen(id,nav = {it->
                    navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
                    navController.navigate(Routes.DpDialogue.toString())
                })
        }
        composable(Routes.Setting.toString()){
            SettingScreen(navController)
        }
        composable(Routes.Profile.toString()){
            ProfileScreen(navController)
        }
        composable(Routes.Home.toString()){
            HomeScreen(nav = {it->
                navController.currentBackStackEntry?.savedStateHandle?.set("cat",it)
                navController.navigate(Routes.DpDialogue.toString())
            },navController)
        }
        dialog(Routes.DpDialogue.toString()){
            val users = navController.previousBackStackEntry?.savedStateHandle?.get<UserProfile>("cat") ?: UserProfile()
            DialogOfDP(users,navController)
        }
    }
}