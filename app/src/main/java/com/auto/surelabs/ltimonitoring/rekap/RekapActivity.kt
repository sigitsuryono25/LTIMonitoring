@file:Suppress("DEPRECATION")

package com.auto.surelabs.ltimonitoring.rekap

import android.app.ProgressDialog
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.CalendarView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.RecyclerView
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import com.auto.surelabs.ltimonitoring.rekap.adapter.AdapterRekap
import com.auto.surelabs.ltimonitoring.utils.Constant
import retrofit2.Call
import retrofit2.Response

class RekapActivity : AppCompatActivity() {
    private var pd: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_rekap)

        supportActionBar?.title = "Rekap Presensi"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val kalenderPicker = findViewById<CalendarView>(R.id.kalenderPicker)
        kalenderPicker.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val tgl = Constant.dateFormat(dayOfMonth)
            val bulan = Constant.dateFormat(month.plus(1))
            val tahun = Constant.dateFormat(year)
            getRekap("$tahun-$bulan-$tgl")
        }
    }

    private fun getRekap(date: String?) {
        pd = ProgressDialog.show(this, "", "memproses permintaan anda...")
        NetworkModules().getService().getDetailRecap(date)
            .enqueue(object : retrofit2.Callback<ResponseRekap> {
                override fun onFailure(call: Call<ResponseRekap>, t: Throwable) {
                    pd?.dismiss()
                    Toast.makeText(this@RekapActivity, t.message.toString(), Toast.LENGTH_SHORT)
                        .show()
                }

                override fun onResponse(
                    call: Call<ResponseRekap>,
                    response: Response<ResponseRekap>
                ) {
                    pd?.dismiss()
                    val rvDetail =
                        this@RekapActivity.findViewById<RecyclerView>(R.id.detail)
                    val titleRekap = this@RekapActivity.findViewById<TextView>(R.id.titleRekap)
                    if (response.isSuccessful) {
                        val data = response.body()?.data
                        if (data?.isEmpty() == false) {
                            val adapter = AdapterRekap(data)
                            rvDetail.itemAnimator = DefaultItemAnimator()
                            titleRekap.visibility = View.VISIBLE
                            rvDetail.adapter = adapter
                            adapter.notifyDataSetChanged()
                        } else {
                            rvDetail.adapter = null
                            titleRekap.visibility = View.GONE
                            Toast.makeText(this@RekapActivity, "Tidak ada data", Toast.LENGTH_SHORT)
                                .show()
                        }
                    } else {
                        Toast.makeText(
                            this@RekapActivity,
                            response.errorBody()?.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            })
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_rekap, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            R.id.help -> {

            }
        }
        return super.onOptionsItemSelected(item)
    }
}
