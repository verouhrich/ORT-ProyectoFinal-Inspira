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
import androidx.core.content.edit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import org.w3c.dom.Text
import java.io.IOException


class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var loginButton: Button
    private lateinit var firebaseServices: FirebaseServices

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        loginButton = findViewById(R.id.loginButton)
        progressBar = findViewById(R.id.loginProgressBar)
        firebaseServices = FirebaseServices()
        loginButton.setOnClickListener { signIn() }
    }

    override fun onStart() {
        super.onStart()
        firebaseServices.cacheSignIn()?.let { firebaseUser ->
            onAuthSuccess(firebaseUser)
        }
    }

    private fun signIn() {
        showProgressBar()
        if (!validateForm()) {
            return
        }
        val email: String = loginEmail.text.toString()
        val password: String = loginPassword.text.toString()
        firebaseServices.signIn(email, password){ firebaseUser ->
            if (firebaseUser != null) onAuthSuccess(firebaseUser)
            else onAuthFailure()
        }
    }

    private fun validateForm(): Boolean {
        var result = true
        val email: String = loginEmail.text.toString()
        val password: String = loginPassword.text.toString()
        if (TextUtils.isEmpty(email)){
            loginEmail.error = "Este campo es obligatorio"
            result = false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail.error = "Ingresa una direccion de email valida"
            result = false
        }
        if (TextUtils.isEmpty(password)){
            loginPassword.error = "Este campo es obligatorio"
            result = false
        }
        return result
    }

    private fun onAuthSuccess(user: FirebaseUser) {
        firebaseServices.getTopic(user){ topic ->
            if (topic.isNullOrEmpty()) {
                onMissingTopic()
                return@getTopic
            }
            firebaseServices.removeOldTopic()
            firebaseServices.subscribeToTopic(topic){ success ->
                if (!success) onSubscriptionFailure()
                else onSubscriptionSuccess(topic)
            }
        }
    }

    private fun showLongToast(message: String) {
        Toast.makeText(baseContext, message, Toast.LENGTH_LONG).show()
    }

    private fun onMissingTopic() {
        showLongToast(getString(R.string.on_missing_topic))
        hideProgressBar()
    }

    private fun onSubscriptionFailure() {
        showLongToast(getString(R.string.on_subscription_failure))
        hideProgressBar()
    }

    private fun onAuthFailure() {
        loginPassword.text.clear()
        showLongToast(getString(R.string.on_auth_failure))
        hideProgressBar()
    }

    private fun onSubscriptionSuccess(topic: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("topic", topic)
        startActivity(intent)
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }
}