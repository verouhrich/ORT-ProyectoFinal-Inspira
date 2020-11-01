package com.ort.inspira

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.notification_item.view.*

class NotifactionsAdapter(val context: Context, val notifications: List<Notification>) : RecyclerView.Adapter< NotifactionsAdapter.MyViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val view = LayoutInflater.from(context).inflate(R.layout.notification_item, parent, false)
        return MyViewHolder(view)
    }

    override fun getItemCount(): Int {
        return notifications.size
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val notification = notifications[position]
        holder.setData(notification, position)

    }
    inner class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        fun setData(notification: Notification?, position: Int){
            itemView.item_title.text = notification!!.title
            itemView.item_body.text = notification!!.body
        }
    }
}