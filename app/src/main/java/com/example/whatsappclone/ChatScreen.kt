package com.example.whatsappclone

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.whatsappclone.data.Message
import com.example.whatsappclone.data.UserProfile
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen(otherUserId: String, nav:(UserProfile)-> Unit) {
    val currentUserId = FirebaseAuth.getInstance().currentUser?.uid ?: ""
    var messageText by remember { mutableStateOf("") }
    var messages by remember { mutableStateOf(listOf<Message>()) }
    val scope = rememberCoroutineScope()
    val listState = rememberLazyListState()
    var otherUserInfo by remember { mutableStateOf<UserProfile?>(null) }

    // Fetch other user's profile
    LaunchedEffect(otherUserId) {
        Firebase.firestore.collection("users")
            .document(otherUserId)
            .get()
            .addOnSuccessListener{ doc ->
                doc.toObject(UserProfile::class.java)?.let {
                    otherUserInfo = it
                }
            }
    }

    // Listen for messages
    LaunchedEffect(Unit){
        receiverMessage(currentUserId, otherUserId,{
            messages = it
            scope.launch {
                if (messages.isNotEmpty()) {
                    listState.scrollToItem(messages.lastIndex)
                }
            }
        })
    }

    Scaffold(
        modifier = Modifier.imePadding(),
        topBar = {
            TopAppBar(
                title = {
                    Row(modifier = Modifier.fillMaxWidth(),verticalAlignment = Alignment.CenterVertically){
                        IconButton(onClick = {
                            Utils.navController.popBackStack()
                        }, modifier = Modifier.size(32.dp)) {
                            Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back",modifier = Modifier.size(32.dp))
                        }
                        Spacer(modifier = Modifier.width(4.dp))
                        AsyncImage(
                            model = otherUserInfo?.url ?: "",
                            contentDescription = "Profile picture",
                            modifier = Modifier
                                .size(60.dp)
                                .clip(CircleShape)
                                .clickable(onClick = {
                                    otherUserInfo?.let {
                                        nav(it)
                                    }
                                }),
                            contentScale = ContentScale.Crop
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = otherUserInfo?.name ?: "",
                            fontWeight = FontWeight.Bold,
                            fontSize = 24.sp
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { },
                        modifier = Modifier.size(40.dp)) {
                        Icon(Icons.Default.MoreVert, contentDescription = "Menu",modifier = Modifier.size(40.dp))
                    }
                }
            )
        },
        bottomBar = {
            MessageInputBar(
                messageText = messageText,
                onMessageChange = {it->
                    messageText = it },
                onSendClick = {
                    if(messageText.isNotBlank()) {
                        sendMessage(currentUserId, otherUserId, messageText)
                        messageText = ""
                    }
                }
            )
        }
    ) { innerPadding ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .background(Color(0xFFEDEDED)),
            state = listState,
            contentPadding = PaddingValues(8.dp)
        ){
            items(messages){ msg ->
                MessageBubble(
                    message = msg.text,
                    isOwnMessage = msg.senderId == currentUserId
                )
            }
        }
    }
}
@Composable
fun MessageBubble(message: String, isOwnMessage: Boolean) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = if (isOwnMessage) Arrangement.End else Arrangement.Start
    ){
        Box(
            modifier = Modifier
                .background(
                    color = if (isOwnMessage) Color(0xFFDCF8C6) else Color.White,
                    shape = RoundedCornerShape(12.dp)
                )
                .padding(10.dp)
                .widthIn(max = 250.dp)
        ) {
            Text(text = message)
        }
    }
    Spacer(modifier = Modifier.height(4.dp))
}

@Composable
fun MessageInputBar(
    messageText: String,
    onMessageChange: (String) -> Unit,
    onSendClick: () -> Unit
){
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        OutlinedTextField(
            value = messageText,
            onValueChange = onMessageChange,
            placeholder = { Text("Type a message") },
            modifier = Modifier
                .weight(1f)
                .padding(end = 8.dp),
            colors = TextFieldDefaults.colors(
                unfocusedContainerColor = Color.White,
                focusedContainerColor = Color.White,
                unfocusedIndicatorColor = Color.Transparent,
                focusedIndicatorColor = Color.Transparent
            ),
            shape = RoundedCornerShape(20.dp)
        )
        IconButton(onClick = onSendClick) {
            Icon(Icons.AutoMirrored.Filled.Send, contentDescription = "Send")
        }
    }
}

