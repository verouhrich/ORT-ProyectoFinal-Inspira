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
import androidx.core.content.ContextCompat
import com.google.firebase.auth.AuthResult
import com.google.firebase.auth.FirebaseAuth
import com.ort.inspira.login.LoginFirebase

class LoginActivity : AppCompatActivity() {
    private lateinit var loginEmail: EditText
    private lateinit var loginPassword: EditText
    private lateinit var progressBar: ProgressBar
    private lateinit var auth: FirebaseAuth
    private lateinit var button: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        loginEmail = findViewById(R.id.loginEmail)
        loginPassword = findViewById(R.id.loginPassword)
        button = findViewById(R.id.loginButton)

        progressBar = findViewById(R.id.loginProgressBar)

        //database = FirebaseDatabase.getInstance()
        auth = FirebaseAuth.getInstance()

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

            val login: LoginFirebase = LoginFirebase()
            login.iniciarSesion<AuthResult>(email, password)
                .addOnCompleteListener(this){
                        task ->
                    if (task.isSuccessful){
                        val userAuth = auth.currentUser
                        Log.d("Usuario uid: ", userAuth.toString())
                        if (userAuth != null) {
                            Log.d("Usuario uid: ", userAuth.uid)
                            startActivity(Intent(this, MainActivity::class.java))
                        }else{
                            startActivity(Intent(this, LoginActivity::class.java))
                        }
                    } else {
                        Toast.makeText(this, "Error en la autenticacion. Verifique que los datos ingresados sean correctos", Toast.LENGTH_LONG).show()
                        startActivity(Intent(this, LoginActivity::class.java))
                        progressBar.visibility= View.INVISIBLE
                    }
                }

        }
    }
}