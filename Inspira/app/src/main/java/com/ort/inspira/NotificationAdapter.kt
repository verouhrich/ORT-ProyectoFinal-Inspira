package com.ort.inspira

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.Timestamp
import kotlinx.android.synthetic.main.notification_item.view.*
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.HashMap

class NotificationAdapter(private val notifications: List<Notification>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification = notifications[position]
        holder.setData(notification)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        fun setData(notification: Notification){
            itemView.item_title.text = notification.title
            itemView.item_body.text = notification.body
            itemView.item_date.text = getDateTime(notification.date)
        }

        private fun getDateTime(stamp: Timestamp): String {
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