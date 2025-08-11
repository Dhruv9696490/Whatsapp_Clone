package com.example.whatsappclone

import android.util.Log
import com.example.whatsappclone.data.Message
import com.google.firebase.Firebase
import com.google.firebase.firestore.firestore
import kotlin.collections.sorted
import kotlin.jvm.java

fun receiverMessage(currentUserId: String, otherUserId: String, callback: (List<Message>) -> Unit) {


    if (currentUserId == otherUserId) {
        Log.e("ChatID", "Error: currentUserId and otherUserId are same!")
        return
    }
    val chatId = Utils.getChatId(currentUserId,otherUserId)
    Log.d("ChatID", "currentUserId=$currentUserId, otherUserId=$otherUserId, chatId=$chatId")
    Firebase.firestore.collection("chats")
        .document(chatId)
        .collection("messages")
        .orderBy("timestamp") // Make sure this field exists in Firestore
        .addSnapshotListener { snapshot, e ->
            if (e != null || snapshot == null) return@addSnapshotListener
            val messages = snapshot.toObjects(Message::class.java)
            callback(messages)
        }
}
