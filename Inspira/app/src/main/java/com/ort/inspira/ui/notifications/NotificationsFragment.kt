package com.ort.inspira.ui.notifications

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
    private var layoutManager: RecyclerView.LayoutManager? = null
    private var adapter: RecyclerView.Adapter<NotificationAdapter.MyViewHolder>? = null
    private lateinit var notificationHistory: NotificationHistory

    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProviders.of(this).get(NotificationsViewModel::class.java)
        notificationHistory = NotificationHistory()
        return inflater.inflate(R.layout.fragment_notifications, container, false)
    }

    override fun onViewCreated(itemView: View, savedInstanceState: Bundle?) {
        super.onViewCreated(itemView, savedInstanceState)
        val sharedPreferences = activity?.getSharedPreferences("settings", AppCompatActivity.MODE_PRIVATE)
        val topic = sharedPreferences?.getString("topic", "")
        Log.d("sharedPreferences.topic", "$topic")
        var _layoutManager = LinearLayoutManager(activity)
        _layoutManager.orientation = LinearLayoutManager.VERTICAL
        _layoutManager.canScrollVertically()
        recyclerView.apply {
            layoutManager = _layoutManager
            notificationHistory.getNotificationHistoryByTopic(topic) {
                adapter = NotificationAdapter(it)
            }
        }
    }

}