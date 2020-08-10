package com.auto.surelabs.ltimonitoring.services

import android.util.Log
import com.auto.surelabs.ltimonitoring.dataclass.Progress
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import org.json.JSONObject

class FirebaseMessagingServices : FirebaseMessagingService() {

    override fun onNewToken(p0: String) {
        super.onNewToken(p0)
        Log.d("onNewToken", p0)
    }

    override fun onMessageReceived(p0: RemoteMessage) {
        super.onMessageReceived(p0)
        if (p0.data.isNotEmpty()) {
            val data = JSONObject(p0.data.toString())
            try {
                if (data.has("masuk")) {
                    val masuk = data.getJSONObject("masuk")
                    NotificationHandle(this).makeNotificationStart(
                        masuk = masuk.getString("masuk"),
                        progress = null
                    )
                } else {
                    val listData = data.getJSONArray("pulang")
                    val progress = Progress()
                    for (i in 0 until listData.length()) {
                        val jObj = listData.getJSONObject(i)
                        progress.type = jObj.getString("type")
                        if (jObj.getString("type") == "1") {
                            progress.jamMulai = jObj.getString("jamMulai")
                            progress.addedOn = jObj.getString("addedOn")
                            progress.id_project = jObj.getString("id_project")
                            progress.jamSelesai = jObj.getString("jamSelesai")
                            progress.ngajarSiapa = jObj.getString("ngajarSiapa")
                        }
                        if (jObj.getString("type") == "2") {
                            progress.projectSiapa = jObj.getString("projectSiapa")
                        }
                        progress.username = jObj.getString("username")
                        progress.progress = jObj.getString("progress")
                        progress.nama = jObj.getString("nama")
                    }

                    NotificationHandle(this).makeNotificationStart(null, progress)
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }

        }

    }
}
