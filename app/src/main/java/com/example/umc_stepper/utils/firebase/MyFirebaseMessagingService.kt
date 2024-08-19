package com.example.umc_stepper.utils.firebase

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.example.umc_stepper.R
import com.example.umc_stepper.ui.login.LoginActivity
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class MyFirebaseMessagingService : FirebaseMessagingService() {

    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d("MyFirebaseMessagingService", "new token : $token")
    }

    // 메시지 수신할 때 호출 됨 -> 수신된 RemoteMessage 객체 기준으로 작업을 수행하고 메시지 데이터 가져올 수 있음
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)

        // Check if message contains data payload
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("MyFirebaseMessagingService", "Message data payload: ${remoteMessage.data}")
            val title = remoteMessage.data["title"] ?: "No Title"
            val body = remoteMessage.data["body"] ?: "No Body"
            sendNotification(title, body)
        } else {
            // Check if message contains a notification payload
            remoteMessage.notification?.let {
                val title = it.title ?: "No Title"
                val body = it.body ?: "No Body"
                sendNotification(title, body)
            }
        }
    }

    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "500"
            val channelName = "MainChannel"
            val channelDescription = "Test Notifications"
            val importance = NotificationManager.IMPORTANCE_HIGH

            val notificationChannel = NotificationChannel(channelId, channelName, importance).apply {
                description = channelDescription
                enableLights(true)
                enableVibration(true)
                setShowBadge(true) // 배지 표시 활성화
            }

            val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun sendNotification(title: String, body: String) {
        createNotificationChannel()

        val channelId = "500"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_stepper_selected)
            .setContentTitle(title)
            .setContentText(body)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setNumber(1) // 배지에 표시할 숫자 설정

        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(1, notificationBuilder.build())
    }

    private fun getPendingIntent(): PendingIntent {
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)

        return PendingIntent.getActivity(
            this,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE
        )
    }

}