package com.auto.surelabs.ltimonitoring.pengumuman

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.notification.Notification
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import com.auto.surelabs.ltimonitoring.utils.HourToMillis
import kotlinx.android.synthetic.main.activity_pengumuman.*
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class PengumumanActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pengumuman)

        supportActionBar?.title = getString(R.string.pengumuman)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)


        button.setOnClickListener {
            if (judulPengumuman.text?.isEmpty() == true || isi.text?.isEmpty() == true) {
                Toast.makeText(
                    this@PengumumanActivity,
                    "Silahkan Isi Semua Kolom",
                    Toast.LENGTH_SHORT
                ).show()
            } else {

                val body = PengumumanItem(
                    id = System.currentTimeMillis().toString(),
                    judul = judulPengumuman.text.toString(),
                    isi = isi.text.toString(),
                    broadcaston = HourToMillis.millisToDate(HourToMillis.millis(), "yyyy-MM-dd")
                )
//                val body = ResponseNotification(
//                    title = judulPengumuman.text.toString(),
//                    body = isi.text.toString()
//                )

                val notification = Notification()
                notification.token = "/topics/pengumuman"
                notification.body = body

                NetworkModules().getFcmService().actionSendService(notification)
                    .enqueue(object : retrofit2.Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                            Log.d("onFailure", t.message.toString())
                        }

                        override fun onResponse(
                            call: Call<ResponseBody>,
                            response: Response<ResponseBody>
                        ) {
                            if (response.isSuccessful) {
                                Log.d("Response", "Success")
                            } else {
                                Log.d("Response", response.errorBody().toString())
                            }
                        }
                    })
            }
        }
    }
}
