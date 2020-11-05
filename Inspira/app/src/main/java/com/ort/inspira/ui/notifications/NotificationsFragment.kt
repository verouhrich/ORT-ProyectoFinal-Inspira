package com.ort.inspira.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.ort.inspira.FirebaseServices
import com.ort.inspira.R

class NotificationsFragment : Fragment() {

    private lateinit var firebaseServices: FirebaseServices
    private var topic: String? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        topic = activity?.intent?.getStringExtra("topic")
        firebaseServices = FirebaseServices()
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        firebaseServices.getNotificationHistoryByTopic(topic!!) { notifications ->
            recyclerView?.adapter = NotificationAdapter(notifications)
        }
    }

}