package com.ort.inspira.ui.notifications

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ort.inspira.NotificationAdapter
import com.ort.inspira.NotificationHistory
import com.ort.inspira.R
import com.ort.inspira.Supplier
import kotlinx.android.synthetic.main.fragment_notifications.*

class NotificationsFragment : Fragment() {

    private lateinit var notificationsViewModel: NotificationsViewModel
    private lateinit var notificationHistory: NotificationHistory
    private var topic: String? = null

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        notificationHistory = NotificationHistory()
        topic = activity?.intent?.getStringExtra("topic")
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        recyclerView.apply {
            layoutManager = LinearLayoutManager(activity)
            notificationHistory.getNotificationHistoryByTopic(topic) {
                if (it.isNotEmpty()) {
                    adapter = NotificationAdapter(it)
                } else {
                    Toast.makeText(activity, "No se recuperaron las notificaciones correctamente", Toast.LENGTH_LONG)
                }
            }
        }
    }

}