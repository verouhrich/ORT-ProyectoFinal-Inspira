package com.ort.inspira

import android.app.Application
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
import com.google.firebase.auth.FirebaseUser

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
        runOnUiThread{
            blockInputs()
            super.onStart()
            val firebaseUser: FirebaseUser? = firebaseServices.cacheSignIn()
            if (firebaseUser != null) onAuthSuccess(firebaseUser)
            else releaseInputs()
        }
    }

    private fun isInternetReachable() {
        Application.NETWORK_STATS_SERVICE
    }

    private fun blockInputs() {
        loginEmail.visibility = View.GONE
        loginPassword.visibility = View.GONE
        loginButton.visibility = View.GONE
        showProgressBar()
    }

    private fun releaseInputs() {
        loginEmail.visibility = View.VISIBLE
        loginPassword.visibility = View.VISIBLE
        loginButton.visibility = View.VISIBLE
        hideProgressBar()
    }

    private fun signIn() {
        runOnUiThread{
            if (!validateForm()) {
                return@runOnUiThread
            }
            showProgressBar()
            val email: String = loginEmail.text.toString()
            val password: String = loginPassword.text.toString()
            firebaseServices.signIn(email, password){ firebaseUser ->
                if (firebaseUser != null) onAuthSuccess(firebaseUser)
                else onAuthFailure()
            }
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
        firebaseServices.signOut()
        hideProgressBar()
    }

    private fun onSubscriptionFailure() {
        showLongToast(getString(R.string.on_subscription_failure))
        firebaseServices.signOut()
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
        hideProgressBar()
    }

    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

}