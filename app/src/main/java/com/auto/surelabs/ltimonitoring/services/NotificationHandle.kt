@file:Suppress("DEPRECATION")

package com.auto.surelabs.ltimonitoring.services

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.auto.surelabs.ltimonitoring.NewMainActivity
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.Progress

class NotificationHandle(
    private val context: Context,
    private val id: Int = Math.random().toInt()
) {
    private val cHANNELID = "myChanel"

    init {
        createNotificationChannel()
    }

    fun makeNotificationStart(masuk: String?, progress: Progress?) {
        val detailIntent = Intent(context, NewMainActivity::class.java)
        detailIntent.putExtra("data", "notif")
        val pendingIntent =
            PendingIntent.getActivity(context, 0, detailIntent, PendingIntent.FLAG_ONE_SHOT)
        var judul = ""
        var content = ""
        if (masuk?.isNotEmpty() == true) {
            judul = "Presensi Masuk"
            content = masuk
        } else {
            judul = "Presensi Pulang"
            content = progress?.nama.toString() + " Presensi Pulang. Klik untuk lihat mitra lainnya"
        }

        val builder = NotificationCompat.Builder(context, cHANNELID)
            .setSmallIcon(R.drawable.ic_income_message)
            .setColor(context.resources.getColor(R.color.colorPrimary))
            .setContentTitle(judul)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setContentIntent(pendingIntent)

        NotificationManagerCompat.from(context).apply {
            notify(id, builder.build())
        }

    }

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "lauwba_channel"
            val descriptionText = "lauwba_channel_desc"
            val importance = NotificationManager.IMPORTANCE_HIGH
            val channel = NotificationChannel(cHANNELID, name, importance).apply {
                description = descriptionText
            }
            // Register the channel with the system
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}