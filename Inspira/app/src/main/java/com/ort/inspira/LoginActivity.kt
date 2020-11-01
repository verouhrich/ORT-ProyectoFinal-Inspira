package com.ort.inspira

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.messaging.FirebaseMessaging
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var loginButton: Button
    private lateinit var firestore: FirebaseFirestore
    private lateinit var usersRef: CollectionReference
    private lateinit var firebaseMessaging: FirebaseMessaging
    private lateinit var firebaseInstaceId: FirebaseInstanceId

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.loginProgressBar)
        auth = FirebaseAuth.getInstance()
        firestore = FirebaseFirestore.getInstance()
        usersRef = firestore.collection("Users")
        firebaseMessaging = FirebaseMessaging.getInstance()
        firebaseInstaceId = FirebaseInstanceId.getInstance()

        loginButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                login()
            }
        })
    }

    override fun onStart() {
        super.onStart()
        spinnerAndButton(spinner = true, button = false)
        if(auth.currentUser != null) {
            getDataUser(auth.currentUser!!.uid)
            action()
        }
        spinnerAndButton(spinner = false, button = true)
    }

    private fun spinnerAndButton(spinner: Boolean, button: Boolean) {
        if (spinner) progressBar.visibility = View.VISIBLE
        else progressBar.visibility = View.GONE
        this.loginButton.isEnabled = button
    }

    private fun login() {
        val email:String=loginEmail.text.toString()
        val password:String=loginPassword.text.toString()
        if(!TextUtils.isEmpty(email) && !TextUtils.isEmpty(password)){
            spinnerAndButton(spinner = true, button = false)
            auth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this){ task ->
                    if (task.isSuccessful){
                        try {
                            val userAuth = auth.currentUser
                            Log.d("Usuario uid: ", userAuth!!.uid)
                            getDataUser(userAuth.uid)
                            action()
                        } catch (error: Exception) {
                            spinnerAndButton(spinner = false, button = true)
                            Toast.makeText(
                                this,
                                "Ocurrio un error, intente mas tarde",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    } else {
                        spinnerAndButton(spinner = false, button = true)
                        loginPassword.text.clear()
                        Toast.makeText(
                            this,
                            "Error en la autenticacion. Verifique que los datos ingresados sean correctos",
                            Toast.LENGTH_LONG
                        ).show()
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
                removeOldTopics()
                subscribeToTopics(topics)
                saveTopic(topics)
            } else {
                Log.d("no document", "No such document")
            }
        }
    }

    private fun saveTopic(topics: ArrayList<String>) {
        val sharedPreferences = getPreferences(MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putString("topic", topics[0])
        Log.d("saveTopic.topic", topics[0]);
        editor.commit()
    }

    private fun removeOldTopics() {
        try {
            Thread {
                firebaseInstaceId.deleteInstanceId()
            }.start()
            Log.d("Lanzo exception?", "No")
        } catch (error: IOException){
            Log.d("Lanzo exception?", "Si")
        }
    }

    private fun subscribeToTopics(topics: ArrayList<String>) {
        topics.forEach { topic ->
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
    }

    private fun action(){
        spinnerAndButton(spinner = false, button = false)
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}