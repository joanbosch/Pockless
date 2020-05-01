package com.pes.pockles.data.messaging

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.pes.pockles.R
import com.pes.pockles.data.FCMTokenManager
import com.pes.pockles.data.repository.UserRepository
import com.pes.pockles.view.ui.MainActivity
import javax.inject.Inject

class PocklesMessagingService @Inject constructor(val userRepository: UserRepository) :
    FirebaseMessagingService() {

    /**
     * Called if InstanceID token is updated. This may occur if the security of
     * the previous token had been compromised. Note that this is called when the InstanceID token
     * is initially generated so this is where you would retrieve the token.
     */
    override fun onNewToken(token: String) {
        //Send token to PAPI
        FirebaseAuth.getInstance().currentUser?.let { userRepository.insertFCMToken(token) }

    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        remoteMessage.data.get("mensaje")
        sendNotification("Viva España", "VIVA FRANCO")
        when (remoteMessage.messageType) {

        }
    }

    override fun onDeletedMessages() {
        var a: Int = 5 + 5
    }

    private fun sendRegistrationToServer(token: String) {
        //TODO
    }

    private fun sendNotification(messageBody: String, title: String) {
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(
            this, 0 /* Request code */, intent,
            PendingIntent.FLAG_ONE_SHOT
        )

        val channelId = "PocklesChanel"
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.ic_pock_icon)
            .setContentTitle(title)
            .setContentText(messageBody)
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setContentIntent(pendingIntent)

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // Since android Oreo notification channel is needed.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel(
                    channelId,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT
                )
            } else {
                TODO("VERSION.SDK_INT < O")
            }
            notificationManager.createNotificationChannel(channel)
        }

        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }
}