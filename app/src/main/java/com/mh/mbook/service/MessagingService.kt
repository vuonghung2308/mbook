package com.mh.mbook.service

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mh.mbook.R
import com.mh.mbook.ui.main.MainActivity

class MessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(message: RemoteMessage) {
        val notification = message.notification
        notification ?: return
        notification.title ?: return
        notification.body ?: return
        showNotification(notification)

    }

    override fun onNewToken(token: String) {
        println(token)
    }

    private fun showNotification(noti: RemoteMessage.Notification) {
        val intent = Intent(baseContext, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)

        val flag = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        } else {
            PendingIntent.FLAG_UPDATE_CURRENT
        }
        val code = 0
        val pIntent = PendingIntent.getActivity(
            baseContext, code, intent, flag
        )

        val channelId = getString(R.string.channel_id)
        val style = NotificationCompat.BigTextStyle().bigText(noti.body)
        val notification = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_outline_book)
            .setContentTitle(noti.title)
            .setContentIntent(pIntent)
            .setAutoCancel(true)
            .setStyle(style)
            .build()
        val manager = getSystemService(Context.NOTIFICATION_SERVICE)
        manager as NotificationManager
        manager.notify(0, notification)
    }
}