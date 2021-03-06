package com.ort.inspira

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import com.ort.inspira.ui.notifications.User
import com.ort.inspira.ui.notifications.Notification
import java.io.IOException

class FirebaseServices {
    private var firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private var firebaseFirestore: FirebaseFirestore = FirebaseFirestore.getInstance()
    private var firebaseMessaging: FirebaseMessaging = FirebaseMessaging.getInstance()
    private var firebaseInstanceId: FirebaseInstanceId = FirebaseInstanceId.getInstance()
    private var usersRef: CollectionReference

    init {
        usersRef = firebaseFirestore.collection("Users")
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
                    val firebaseUser: FirebaseUser? = task.result!!.user
                    returnData(firebaseUser)
                } else {
                    returnData(null)
                }
            }
    }

    fun getUser(firebaseUser: FirebaseUser, returnData: (User?) -> Unit) {
        usersRef.document(firebaseUser.uid).get().addOnCompleteListener { task ->
            if (task.isSuccessful) {
                if (task.result!!.exists()) {
                    val userDocument: User? = task.result!!.toObject(User::class.java)
                    returnData(userDocument)
                } else {
                    returnData(null)
                }
            }
        }
    }

    fun removeOldTopic() {
        Thread{
            try {
                firebaseInstanceId.deleteInstanceId()
            } catch (exception: IOException) {
            }
        }.start()
    }

    fun subscribeToTopic(topic: String, returnData: (Boolean) -> Unit) {
        firebaseMessaging.subscribeToTopic(topic).addOnCompleteListener { task ->
            if (task.isSuccessful) returnData(true)
            else returnData(false)
        }
    }

    fun getNotifications(topic: String?, returnData: (List<Notification>?) -> Unit) {
        if (topic.isNullOrEmpty()) returnData(null)
        usersRef.whereEqualTo("topic", topic)
            .limit(1)
            .get().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val documents: List<DocumentSnapshot> = task.result!!.documents
                    if (documents.isNotEmpty()) {
                        returnData(documents[0].toObject(User::class.java)?.notifications)
                    } else {
                        returnData(emptyList())
                    }
                } else {
                    returnData(null)
                }
            }
    }
}