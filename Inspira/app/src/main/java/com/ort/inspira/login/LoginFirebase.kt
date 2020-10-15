package com.ort.inspira.login

import android.content.Intent
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ort.inspira.LoginActivity
import com.ort.inspira.MainActivity

class LoginFirebase : LoginAdapter{

    override fun <AuthResult> iniciarSesion(email: String, password: String): Task<AuthResult> {

        val auth: FirebaseAuth = FirebaseAuth.getInstance()
        return auth.signInWithEmailAndPassword(email, password) as Task<AuthResult>
    }
}