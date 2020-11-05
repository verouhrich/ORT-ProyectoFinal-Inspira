package com.ort.inspira.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.ort.inspira.R
import org.w3c.dom.Text

class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val missionTitle: TextView = root.findViewById(R.id.missionTitle)
        val missionDescription: TextView = root.findViewById(R.id.missionDescription)
        val visionTitle: TextView = root.findViewById(R.id.visionTitle)
        val visionDescription: TextView = root.findViewById(R.id.visionDescription)
        homeViewModel.missionTitle.observe(viewLifecycleOwner, Observer {
            missionTitle.text = it
        })
        homeViewModel.missionDescription.observe(viewLifecycleOwner, Observer {
            missionDescription.text = it
        })
        homeViewModel.visionTitle.observe(viewLifecycleOwner, Observer {
            visionTitle.text = it
        })
        homeViewModel.visionDescription.observe(viewLifecycleOwner, Observer {
            visionDescription.text = it
        })
        return root
    }
}