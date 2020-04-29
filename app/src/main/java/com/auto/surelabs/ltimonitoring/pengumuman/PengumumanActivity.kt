package com.auto.surelabs.ltimonitoring.pengumuman

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.login.ResponseLogin
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


        btnKirim.setOnClickListener {
            if (judulPengumuman.text?.isEmpty() == true || isi.text?.isEmpty() == true) {
                Toast.makeText(
                    this@PengumumanActivity,
                    "Silahkan Isi Semua Kolom",
                    Toast.LENGTH_SHORT
                ).show()
            } else {
                btnKirim.isEnabled = false
                btnKirim.text = getString(R.string.sending_notice)
                val idPengumuman = HourToMillis.millis().toString()
                val body = PengumumanItem(
                    id = idPengumuman,
                    judul = judulPengumuman.text.toString(),
                    isi = isi.text.toString(),
                    broadcaston = HourToMillis.millisToDate(HourToMillis.millis(), "yyyy-MM-dd")
                )

                val notification = Notification()
                notification.token = "/topics/pengumuman"
//                notification.token = "/topics/pengumumandebug"
                notification.body = body

                NetworkModules().getService().insertPengumuman(
                    idPengumuman = idPengumuman,
                    judul = judulPengumuman.text.toString(),
                    isi = isi.text.toString(),
                    broadcaston = HourToMillis.millisToDate(HourToMillis.millis(), "yyyy-MM-dd")
                ).enqueue(object : retrofit2.Callback<ResponseLogin> {
                    override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    }

                    override fun onResponse(
                        call: Call<ResponseLogin>,
                        response: Response<ResponseLogin>
                    ) {
                        if (response.body()?.code == 200) {
                            pushNotif(notification)
                        }
                    }

                })


            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun pushNotif(notification: Notification) {
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
                        AlertDialog.Builder(this@PengumumanActivity)
                            .setMessage("Pengumuman Berhasil Disebarkan")
                            .setPositiveButton("Okay") { d, _ ->
                                d.dismiss()
                                judulPengumuman.setText("")
                                isi.setText("")
                                btnKirim.isEnabled = true
                                btnKirim.text = resources.getString(R.string.kirim_pengumuman)

                            }
                            .create()
                            .show()
                    } else {
                        Log.d("Response", response.errorBody().toString())
                    }
                }
            })
    }
}
