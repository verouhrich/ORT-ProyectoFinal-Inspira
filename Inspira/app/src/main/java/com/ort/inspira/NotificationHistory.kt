package com.ort.inspira

import android.util.Log
import com.google.api.ResourceDescriptor
import com.google.firebase.firestore.FirebaseFirestore

class NotificationHistory {
    private val db = FirebaseFirestore.getInstance()

    fun getNotificationHistoryByTopic(topic: String?, myCallback: (List<Notification>) -> Unit) {
        Log.d("getHistory.topic", "$topic")
        db.collection("History").whereEqualTo("topic", topic)
            .limit(1)
            .get().addOnCompleteListener{ task ->
                if (task.isSuccessful) {
                    val emptyList = task.result!!.documents.isEmpty()
                    if (emptyList) myCallback(emptyList())
                    else {
                        val notifications: List<Notification> = task.result!!.documents[0].toObject(HistoryDocument::class.java)!!.notifications
                        myCallback(notifications)
                    }
                }
            }
    }

}