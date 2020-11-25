package com.ort.inspira.ui.notifications

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import com.ort.inspira.R
import kotlinx.android.synthetic.main.notification_item.view.*
import java.text.SimpleDateFormat
import java.util.*

class NotificationAdapter(private val notifications: List<Notification>?) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        var itemCount = 0
        if (notifications != null) {
            itemCount = notifications.size
        }
        return itemCount
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification: Notification? = notifications?.get(position)
        holder.setData(notification)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(notification: Notification?){
            itemView.item_title.text = notification?.title
            itemView.item_title.setBackgroundColor(Color.parseColor(notification?.color))
            itemView.item_body.text = notification?.body
            itemView.item_date.text = getDateTime(notification?.date)
        }

        private fun getDateTime(stamp: Timestamp?): String {
            if (stamp == null) return ""
            return try {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val netDate = Date(stamp.seconds * 1000)
                sdf.format(netDate)
            } catch (e: Exception) {
                e.toString()
            }
        }
    }
}