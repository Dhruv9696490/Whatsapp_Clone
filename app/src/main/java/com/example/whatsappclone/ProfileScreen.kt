package com.example.whatsappclone

import android.R.attr.fontWeight
import android.content.Context
import android.net.Uri
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.clickable
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.List
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.whatsappclone.data.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import org.json.JSONObject
import java.io.IOException


fun uploadToCloudinary(context: Context, imageUri: Uri, onResult: (String?) -> Unit) {
    val cloudName = "dvg3vkutf"
    val uploadPreset = "ml_default"

    val contentResolver = context.contentResolver
    val inputStream = contentResolver.openInputStream(imageUri) ?: return
    val requestBody = inputStream.readBytes()
        .toRequestBody("image/*".toMediaTypeOrNull())

    val client = OkHttpClient()
    val request = Request.Builder()
        .url("https://api.cloudinary.com/v1_1/$cloudName/image/upload")
        .post(
            MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("upload_preset", uploadPreset)
                .addFormDataPart("file", "image.jpg", requestBody)
                .build()
        ).build()


    client.newCall(request).enqueue(object : Callback {
        override fun onFailure(call: Call, e: IOException) {
            onResult(null)
        }

        override fun onResponse(call: Call, response: Response) {
            val json = JSONObject(response.body?.string() ?: "")
            val url = json.optString("secure_url")
            onResult(url)
        }

    })
}
@Composable
fun ProfileScreen(navHostController: NavHostController){
    var userProfile: UserProfile? by remember { mutableStateOf(UserProfile()) }
    var userName by remember { mutableStateOf("") }
    var userAbout by remember { mutableStateOf("") }
    var userLinks by remember { mutableStateOf("") }
    val context= LocalContext.current
    DisposableEffect(Unit){
        val listenerRegistration= Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!)
            .addSnapshotListener{it,_->
                if(it!=null){
                    userProfile=it.toObject(UserProfile::class.java)
                    userName=userProfile?.name!!
                    userAbout=userProfile?.about!!
                    userLinks=userProfile?.links!!
                }
            }
        onDispose {
            listenerRegistration.remove()
        }
    }


    var nameEnable by remember { mutableStateOf(false) }
    var aboutEnable by remember { mutableStateOf(false) }
    var linkEnable by remember { mutableStateOf(false) }
    val imageLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent(),
        onResult = {uri ->
            uri?.let {
                // Upload to Cloudinary
                uploadToCloudinary(context, it) { cloudUrl ->
                    cloudUrl?.let { finalUrl ->
                        Firebase.firestore.collection("users")
                            .document(FirebaseAuth.getInstance().currentUser?.uid!!)
                            .update("url", finalUrl)
                    }
                }
            }
            }
    )
    Column(modifier = Modifier.fillMaxSize().padding(top = 40.dp, bottom = 8.dp)){
        Row(modifier = Modifier.padding(horizontal = 8.dp)
            ,verticalAlignment = Alignment.CenterVertically) {
            IconButton(
                onClick = {
                    navHostController.popBackStack()
                },
                modifier = Modifier.size(32.dp)
            ) {
                Icon(
                    Icons.AutoMirrored.Filled.ArrowBack, null,
                    modifier = Modifier.size(28.dp)
                )
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(" Profile", fontSize = 24.sp, fontWeight = FontWeight.Bold)
        }
        Spacer(modifier = Modifier.height(8.dp))
        AsyncImage(model = userProfile?.url!!,null,
            modifier = Modifier.size(200.dp).clip(CircleShape).align(alignment = Alignment.CenterHorizontally)
                .clickable{
                    imageLauncher.launch("image/*")
                },
            contentScale = ContentScale.Crop)
        Spacer(modifier = Modifier.height(16.dp))
        Text("Edit",
            modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
            fontSize = 24.sp,
            color = colorResource(R.color.light_green),
            fontWeight = FontWeight.SemiBold
        )
        Spacer(modifier = Modifier.height(16.dp))
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp).clickable(
            onClick = {nameEnable=true}
        ),
            verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Default.AccountCircle,null, modifier = Modifier.size(40.dp),
                tint = Color.DarkGray)
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Name", fontSize = 24.sp)
                Spacer(Modifier.height(0.dp))
                if(!nameEnable){
                    Text(userProfile?.name!!,
                        modifier = Modifier)
                }else{
                    TextField(value = userName,
                        onValueChange = {userName=it},
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    Firebase.firestore.collection("users").document(userProfile?.uid!!)
                                        .update("name",userName)
                                    nameEnable=!nameEnable
                                }
                            ){
                                Icon(Icons.Default.Search,null, modifier = Modifier.size(24.dp))
                            }
                        })
                }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp).clickable(
            onClick = {aboutEnable=true}
        ),
            verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Default.Info,null, modifier = Modifier.size(40.dp),
                tint = Color.DarkGray)
            Spacer(Modifier.width(16.dp))
            Column {
                Text("About", fontSize = 24.sp)
                Spacer(Modifier.height(0.dp))
                if (!aboutEnable){
                Text(userProfile?.about!!,
                    modifier = Modifier)
            }else{
            TextField(value = userAbout,
                onValueChange = {userAbout=it},
                modifier = Modifier.fillMaxWidth(),
                trailingIcon = {
                    IconButton(
                        onClick = {
                            Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!)
                                .update("about",userAbout)
                            aboutEnable=!aboutEnable

                        }
                    ){
                        Icon(Icons.Default.Search,null, modifier = Modifier.size(24.dp))
                    }
                })
        }
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp),
            verticalAlignment = Alignment.CenterVertically){
            Icon(Icons.Default.Email,null, modifier = Modifier.size(40.dp),
                tint = Color.DarkGray)
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Email", fontSize = 24.sp)
                Spacer(Modifier.height(0.dp))
                Text(FirebaseAuth.getInstance().currentUser?.email!!)
            }
        }
        Row(modifier = Modifier.fillMaxWidth().padding(16.dp).clickable(onClick = {linkEnable=true}),
            verticalAlignment = Alignment.CenterVertically){
            Icon(
                Icons.AutoMirrored.Filled.List,null, modifier = Modifier.size(40.dp),
                tint = Color.DarkGray)
            Spacer(Modifier.width(16.dp))
            Column {
                Text("Links", fontSize = 24.sp)
                Spacer(Modifier.height(0.dp))
                if(!linkEnable){
                    Text(userProfile?.links!!,
                        modifier = Modifier
                    )
                }else{
                    TextField(value =userLinks,
                        onValueChange = {userLinks=it},
                        modifier = Modifier.fillMaxWidth(),
                        trailingIcon = {
                            IconButton(
                                onClick = {
                                    Firebase.firestore.collection("users").document(FirebaseAuth.getInstance().currentUser?.uid!!)
                                        .update("links",userLinks)
                                    linkEnable=!linkEnable
                                }
                            ){
                                Icon(Icons.Default.Search,null, modifier = Modifier.size(24.dp))
                            }
                        })
                }
            }
        }
    }
}