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

class NotificationAdapter(val notifications: List<HashMap<String, Any>>) : RecyclerView.Adapter<NotificationAdapter.MyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification = notifications[position]
        holder.setData(notification, position)
    }

    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        @SuppressLint("SetTextI18n")
        fun setData(notification: HashMap<String, Any>, position: Int){
            itemView.item_title.text = notification["title"] as String
            itemView.item_body.text = notification["body"] as String
            itemView.item_date.text = getDateTime(notification["date"] as Timestamp)
        }

        private fun stampToDate(stamp: Timestamp): String {
            return stamp.toDate().toString()
        }

        private fun getDateTime(stamp: Timestamp): String? {
            try {
                val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm")
                val netDate = Date(stamp.seconds * 1000)
                return sdf.format(netDate)
            } catch (e: Exception) {
                return e.toString()
            }
        }

    }
}