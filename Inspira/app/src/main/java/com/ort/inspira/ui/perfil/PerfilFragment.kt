package com.ort.inspira.ui.perfil

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ort.inspira.FirebaseServices
import com.ort.inspira.LoginActivity
import com.ort.inspira.MainActivity
import com.ort.inspira.R

class PerfilFragment : Fragment() {

    private lateinit var perfilViewModel: PerfilViewModel
    private lateinit var firebaseServices: FirebaseServices

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        perfilViewModel =
            ViewModelProviders.of(this).get(PerfilViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_perfil, container, false)
        val textView: TextView = root.findViewById(R.id.text_tips)
        perfilViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = "$it ${activity?.intent?.getStringExtra("topic")}"
        })
        val logoutButton: Button = root.findViewById(R.id.logoutButton)
        firebaseServices = FirebaseServices()
        logoutButton.setOnClickListener { signOut() }
        return root
    }

    private fun signOut() {
        firebaseServices.signOut()
        activity?.finishAffinity()
        val intent = Intent(context, LoginActivity::class.java)
        startActivity(intent)
    }
}