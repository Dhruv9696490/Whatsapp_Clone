package com.example.whatsappclone.welcomeScreens

import android.R.attr.name
import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.whatsappclone.data.UserProfile
import com.google.android.play.core.integrity.au
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.firestore.firestore

class ViewModelWhatsApp: ViewModel(){
    private val _whatsAppState = mutableStateOf<WhatsAppState?>(null)
    val whatsAppState: State<WhatsAppState?> = _whatsAppState
    val auth = Firebase.auth
    fun sign(name:String,email: String,password: String){
        _whatsAppState.value= WhatsAppState.Loading
        auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {result->
            if(result.isSuccessful){
                _whatsAppState.value= WhatsAppState.Authenticated
                val id= result.result?.user?.uid
                Firebase.firestore.collection("users")
                    .document(id!!).set(
                        UserProfile(message = "hello",
                            name = name,
                            time = "10:34",
                            uid = id,
                            url = "https://res.cloudinary.com/dvg3vkutf/image/upload/v1754678207/a04d849cf591c2f980548b982f461401_idmgct.jpg",
                            about = "Hey there! I am using WhatsApp.",
                            links = "Add Links")
                    )
            }else{
                _whatsAppState.value= WhatsAppState.Error(error = result.exception?.localizedMessage!!)
            }
        }
    }
    fun signOut(){
        auth.signOut()
        _whatsAppState.value= WhatsAppState.UnAuthenticated
    }
    fun login(email: String,password: String){
        _whatsAppState.value= WhatsAppState.Loading
        auth.signInWithEmailAndPassword(email,password).addOnCompleteListener {result->
            if(result.isSuccessful){
                _whatsAppState.value= WhatsAppState.Authenticated
            }else{
                _whatsAppState.value= WhatsAppState.Error(error = result.exception?.localizedMessage!!)
            }
        }
    }
}

sealed class WhatsAppState{
    object Authenticated: WhatsAppState()
    object UnAuthenticated: WhatsAppState()
    object Loading: WhatsAppState()
    data class Error(val error: String): WhatsAppState()
}