package com.ort.inspira

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.view.WindowManager
import android.widget.ProgressBar
import com.google.firebase.auth.FirebaseUser

class SplashScreenActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)
        val splashProgressBar: ProgressBar = findViewById(R.id.splashProgressBar)
        splashProgressBar.visibility = View.VISIBLE
        Handler().postDelayed ( Runnable() {
            val firebaseUser: FirebaseUser? = firebaseServices.cacheSignIn()
            if (firebaseUser == null) {
                startActivity(Intent(this, LoginActivity::class.java))
                finish()
            }
            else onAuthSuccess(firebaseUser)
        }, 1000)
    }
}