package com.ort.inspira

import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore

class NotificationHistory {
    private val db = FirebaseFirestore.getInstance()

    fun getNotificationHistoryByTopic(topic: String?, myCallback: (List<HashMap<String, Any>>) -> Unit) {
        Log.d("getHistory.topic", "$topic")
       val querySnapshot = db.collection("History").whereEqualTo("topic", topic)
           .limit(1)
           .get().addOnCompleteListener{ task ->
               if (task.isSuccessful) {
                   val emptyList = task.result!!.documents.isEmpty()
                   if (emptyList) myCallback(emptyList())
                   else {
                       val list = task.result!!.documents[0].get("notifications") as List<HashMap<String, Any>>
                       myCallback(list)
                   }
               }
           }
    }

}