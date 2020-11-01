package com.ort.inspira

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class NotificationHistory {
    private val db = FirebaseFirestore.getInstance()

    fun getNotificationHistoryByTopic(topic: String?, myCallback: (List<HashMap<String, Any>>) -> Unit) {
       val querySnapshot = db.collection("History").whereEqualTo("topic", topic)
           .limit(1)
           .get().addOnCompleteListener{ task ->
               if (task.isSuccessful) {
                   val list = task.result!!.documents[0].get("notifications") as List<HashMap<String, Any>>
                   myCallback(list)
               }
           }
    }

}