package com.ort.inspira

import android.util.Log
import com.google.android.gms.tasks.Task
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot

class NotificationsHistory {
    private val db = FirebaseFirestore.getInstance()


    fun showNotifications(topic: String): Task<QuerySnapshot>{
        return db.collection("History").whereEqualTo("topic", topic).limit(1)
            .get()
    }

    /*
        fun showNotifications(topic: String){
        val db = FirebaseFirestore.getInstance()
        db.collection("History").whereEqualTo("topic", topic).limit(1)
            .get()
                .addOnSuccessListener { documents ->
                    for (document in documents){
                        Log.d("Notifaction", "Notification: ${document.data}")
                    }
            }
            .addOnFailureListener { exception ->
                Log.w("Error", "Error getting documents: ", exception)
            }
    }
     */

}