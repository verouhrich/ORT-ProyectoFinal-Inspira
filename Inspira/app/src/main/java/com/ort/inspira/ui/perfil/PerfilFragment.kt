package com.ort.inspira.ui.perfil

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ort.inspira.R


class TipsFragment : Fragment() {

    private lateinit var perfilViewModel: PerfilViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        perfilViewModel =
            ViewModelProviders.of(this).get(PerfilViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_tips, container, false)
        val textView: TextView = root.findViewById(R.id.text_tips)
        perfilViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}