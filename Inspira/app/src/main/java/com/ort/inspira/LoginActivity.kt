package com.ort.inspira

import android.content.Intent
import android.opengl.Visibility
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException

class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var button: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var usersRef: CollectionReference
    private lateinit var firebaseMessaging: FirebaseMessaging
    private lateinit var firebaseInstaceId: FirebaseInstanceId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        button = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.loginProgressBar)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        usersRef = firestore.collection("Users")
        firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseInstaceId = FirebaseInstanceId.getInstance()

        button.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                login()
            }
        })
    }

    private fun login() {
        val email:String=loginEmail.text.toString()
        val password:String=loginPassword.text.toString()
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            progressBar.visibility = View.VISIBLE

            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){
                        task ->
                    if (task.isSuccessful){
                        val userAuth = auth.currentUser
                        if (userAuth != null) {
                            Log.d("Usuario uid: ", userAuth.uid)
                            getDataUser(userAuth.uid)
                        }
                    } else {
                        Toast.makeText(this, "Error en la autenticacion. Verifique que los datos ingresados sean correctos", Toast.LENGTH_LONG).show()
                        loginPassword.text.clear()
                        progressBar.visibility = View.GONE
                    }
                }
        }
    }

    private fun getDataUser(uid: String) {
        if (uid.isNullOrEmpty()) return
        val userRef = usersRef.document(uid)
        userRef.get().addOnSuccessListener { document ->
            if (document != null) {
                val topics = document.get("topics") as ArrayList<String>
                Log.d("data", "DocumentSnapshot data: $topics")
                removeOldTopics(topics)
            } else {
                Log.d("no document", "No such document")
            }
        }
    }

    private fun removeOldTopics(topics: ArrayList<String>) {
        try {
            firebaseInstaceId.deleteInstanceId()
        } catch (error: IOException){
            Log.d("Maybe Reinstall?", "Reinstall app go")
        } finally {
            Log.d("finally?", "finally.")
            subscribeToTopics(topics)
        }
    }

    private fun subscribeToTopics(topics: ArrayList<String>) {
        topics.forEach {topic ->
            if (topic is String) {
                firebaseMessaging.subscribeToTopic(topic)
                    .addOnCompleteListener  { task ->
                        if (task.isSuccessful) {
                            Log.d("Toast: $topic", "OK")
                        } else {
                            Log.d("Toast $topic", "FAIL")
                        }
                    }
            }
        }
        action(topics)
    }

    private fun action(topics: ArrayList<String>){
        progressBar.visibility = View.GONE
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("topic", topics[0])
        startActivity(intent)
    }
}