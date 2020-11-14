package com.ort.inspira

import android.app.Application
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.ort.inspira.ui.notifications.User

class LoginActivity : BaseActivity() {

    private lateinit var loginButton: Button

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

    override fun onBackPressed() {
        finish()
    }

    private fun signIn() {
        runOnUiThread{
            if (!validateForm()) {
                return@runOnUiThread
            }
            showProgressBar()
            val email: String = loginEmail!!.text.toString()
            val password: String = loginPassword!!.text.toString()
            firebaseServices.signIn(email, password) { firebaseUser ->
                if (firebaseUser != null) onAuthSuccess(firebaseUser)
                else onAuthFailure()
            }
        }
    }

    private fun validateForm(): Boolean {
        var result = true
        val email: String = loginEmail!!.text.toString()
        val password: String = loginPassword!!.text.toString()
        if (TextUtils.isEmpty(email)){
            loginEmail!!.error = "Este campo es obligatorio"
            result = false
        }
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            loginEmail!!.error = "Ingresa una direccion de email valida"
            result = false
        }
        if (TextUtils.isEmpty(password)){
            loginPassword!!.error = "Este campo es obligatorio"
            result = false
        }
        return result
    }

}