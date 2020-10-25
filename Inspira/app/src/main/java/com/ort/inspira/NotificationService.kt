package com.ort.inspira

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.util.Log.d
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.logging.Logger
import kotlin.random.Random


class NotificationService : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val title = remoteMessage.notification?.title
        Log.d("remoteMessage.title", "$title")
        val body = remoteMessage.notification?.body
        Log.d("remoteMessage.body", "$body")
        val imageUrl = remoteMessage.notification?.imageUrl?.toString()
        Log.d("remoteMessage.imageUrl", "$imageUrl")
        val image = getBitmapFromURL(imageUrl)
        Log.d("remoteMessage.image", "$image")
        val color = remoteMessage.notification?.color
        Log.d("remoteMessage.color", "$color")
        triggerNotification(title, body, image, color)
    }

    private fun getBitmapFromURL(imageUrl: String?): Bitmap? {
        if (imageUrl.isNullOrEmpty()) return null
        return try {
            val url: URL = URL(imageUrl)
            val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
            connection.connect()
            val input: InputStream =  connection.inputStream
            BitmapFactory.decodeStream(input)
        } catch (error: IOException) {
            error.printStackTrace()
            null
        }
    }

    private fun triggerNotification(title: String?, body: String?, image: Bitmap?, color: String?) {
        val channelId = getString(R.string.default_notification_channel_id)
        val notificationBuilder : NotificationCompat.Builder = NotificationCompat.Builder(this, channelId)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(NotificationCompat.BigTextStyle().bigText(body))
            .setColor(Color.parseColor(color))
        if (image != null){
            notificationBuilder.setStyle(NotificationCompat.BigPictureStyle().bigPicture(image))
        }

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(channelId,
                "Default",
                NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(System.currentTimeMillis().toInt(), notificationBuilder.build())
    }
}
