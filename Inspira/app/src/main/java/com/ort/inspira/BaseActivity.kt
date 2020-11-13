package com.ort.inspira

import android.content.Intent
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.ktx.Firebase
import com.ort.inspira.ui.notifications.User

open class BaseActivity : AppCompatActivity()  {

    var loginEmail: EditText? = null
    var loginPassword: EditText? = null
    var progressBar: ProgressBar? = null
    var firebaseServices: FirebaseServices = FirebaseServices()

    fun onAuthSuccess(firebaseUser: FirebaseUser) {
        runOnUiThread{
            firebaseServices.getUser(firebaseUser) getTopic@{ user ->
                if (user != null && !user.topic.isNullOrEmpty()){
                    firebaseServices.removeOldTopic()
                    firebaseServices.subscribeToTopic(user.topic){ success ->
                        if (!success) onSubscriptionFailure()
                        else onSubscriptionSuccess(user)
                    }
                } else {
                    onMissingTopic()
                }
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

    fun onAuthFailure() {
        loginPassword?.text?.clear()
        showLongToast(getString(R.string.on_auth_failure))
        hideProgressBar()
    }

    private fun onSubscriptionSuccess(user: User) {
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("topicDescription", user.topicDescription)
        intent.putExtra("topic", user.topic)
        startActivity(intent)
        hideProgressBar()
        finish()
    }

    fun showProgressBar() {
        progressBar?.visibility = View.VISIBLE
    }

    private fun hideProgressBar() {
        progressBar?.visibility = View.GONE
    }
}