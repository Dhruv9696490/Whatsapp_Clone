package com.example.whatsappclone

import android.util.Log
import com.example.whatsappclone.data.Message
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.collections.sorted


fun sendMessage(sendId: String, receiverId: String, msg: String){
    val msgId = Utils.getChatId(sendId,receiverId)
    Log.d("ChatID", "currentUserId1=$sendId, otherUserId1=$receiverId, chatId1ch=$msgId")

    if (sendId == receiverId) {
        Log.e("ChatID", "Error: currentUserId and otherUserId are same!")
        return
    }
    val message = Message(senderId = sendId,receiverId=receiverId, text = msg,
        timestamp = System.currentTimeMillis())
        Firebase.firestore.collection("chats")
            .document(msgId).collection("messages")
            .add(message).addOnSuccessListener {
                Log.d("Chat", "Message sent!")
            }
            .addOnFailureListener {
                Log.e("Chat", "Error sending message", it)
            }
}
