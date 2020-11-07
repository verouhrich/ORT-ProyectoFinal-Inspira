package com.ort.inspira

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.ort.inspira.ui.notifications.HistoryDocument
import com.ort.inspira.ui.notifications.Notification
import org.w3c.dom.Document
import java.io.IOException

class FirebaseServices {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var firebaseMessaging: FirebaseMessaging = FirebaseMessaging.getInstance()
    private var firebaseInstanceId: FirebaseInstanceId = FirebaseInstanceId.getInstance()
    private var usersRef: CollectionReference
    private var historyRef: CollectionReference

    init {
        usersRef = firebaseFirestore.collection("Users")
        historyRef = firebaseFirestore.collection("History")
    }

    fun cacheSignIn(): FirebaseUser? {
        return firebaseAuth.currentUser
    }

    fun signOut() {
        removeOldTopic()
        firebaseAuth.signOut()
    }

    fun signIn(email: String, password: String, returnData: (user: FirebaseUser?) -> Unit) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val firebaseUser: FirebaseUser? = task.result?.user
                    returnData(firebaseUser)
                } else {
                    returnData(null)
                }
            }
    }

    fun getTopic(user: FirebaseUser, returnData: (String?) -> Unit) {
        usersRef.document(user!!.uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result!!.exists() && task.result?.get("topic") != null) {
                    val topic: String? = task.result?.get("topic") as String?
                    returnData(topic)
                } else {
                    returnData(null)
                }
            }
        }
    }

    fun removeOldTopic() {
        try {
            Thread {
                firebaseInstanceId.deleteInstanceId()
            }.start()
        } catch (error: IOException) {
        }
    }

    fun subscribeToTopic(topic: String, returnData: (Boolean) -> Unit) {
        firebaseMessaging.subscribeToTopic(topic).addOnCompleteListener { task ->
            if (task.isSuccessful) returnData(true)
            else returnData(false)
        }
    }

    fun getNotificationHistoryByTopic(topic: String?, returnData: (List<Notification>?) -> Unit) {
        if (topic.isNullOrEmpty()) returnData(emptyList())
        historyRef.whereEqualTo("topic", topic)
            .limit(1)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents: List<DocumentSnapshot> = task.result!!.documents
                    if (documents.isNotEmpty()) {
                        returnData(documents[0].toObject(HistoryDocument::class.java)?.notifications)
                    } else {
                        returnData(emptyList())
                    }
                } else {
                    returnData(null)
                }
            }
    }
}