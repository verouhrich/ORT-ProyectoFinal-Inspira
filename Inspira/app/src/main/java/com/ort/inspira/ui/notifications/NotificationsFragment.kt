package com.ort.inspira.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.ktx.Firebase
import com.ort.inspira.FirebaseServices
import com.ort.inspira.R
import com.ort.inspira.ui.home.HomeViewModel

class NotificationsFragment : Fragment() {

    private lateinit var firebaseServices: FirebaseServices
    private lateinit var notificationsViewModel: NotificationsViewModel
    private var topic: String? = null
    private lateinit var emptyListMessage: TextView
    private lateinit var root: View

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        topic = activity?.intent?.getStringExtra("topic")
        firebaseServices = FirebaseServices()
        root = inflater.inflate(R.layout.fragment_notifications, container, false)
        emptyListMessage = root.findViewById(R.id.emptyList)
        notificationsViewModel = ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        return root
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        firebaseServices.getNotifications(topic) { notifications ->
            if (notifications != null) {
                if (notifications.isNotEmpty()) {
                    recyclerView?.adapter = NotificationAdapter(notifications)
                } else {
                    notificationsViewModel.emptyList.observe(viewLifecycleOwner, Observer {
                        emptyListMessage.text = it
                    })
                }
            } else {
                showLongToast(getString(R.string.notificationsError))
            }
        }
    }

    private fun showLongToast(message: String) {
        Toast.makeText(activity?.baseContext, message, Toast.LENGTH_LONG).show()
    }
}