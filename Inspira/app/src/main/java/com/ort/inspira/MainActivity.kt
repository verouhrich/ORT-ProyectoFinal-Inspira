package com.ort.inspira

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.google.android.material.bottomnavigation.BottomNavigationView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        val TAG: String="Conectado1"
        val auth: FirebaseAuth = Firebase.auth
        val db = FirebaseFirestore.getInstance()

        auth.signInWithEmailAndPassword("profesor@ort.com","profesor")
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("task", "signInWithEmail:success")
                    val user = auth.currentUser
                    if (user != null) {
                        Log.d("usuario", user.uid)
                    }
                }
            }

        FirebaseMessaging.getInstance().subscribeToTopic("Device")
            .addOnCompleteListener { task ->
                Log.d(TAG, "fruta se hizo")
                Toast.makeText(baseContext, "Fruta Toast", Toast.LENGTH_SHORT).show()
            }

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)


        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_tips, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}