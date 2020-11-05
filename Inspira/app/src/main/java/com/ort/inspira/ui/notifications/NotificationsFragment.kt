package com.ort.inspira.ui.notifications

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.inspira.NotificationAdapter
import com.ort.inspira.HistoryDocument
import com.ort.inspira.NotificationHistory
import com.ort.inspira.R
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationHistory: NotificationHistory
    private var topic: String? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        topic = activity?.intent?.getStringExtra("topic")
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        notificationHistory = NotificationHistory()
        val recyclerView: RecyclerView? = view?.findViewById(R.id.recyclerView)
        recyclerView?.layoutManager = LinearLayoutManager(context)
        notificationHistory.getNotificationHistoryByTopic(topic) { notifications ->
            recyclerView?.adapter = NotificationAdapter(notifications)
        }
    }

}