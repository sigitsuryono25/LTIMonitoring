package com.auto.surelabs.ltimonitoring.utils

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.Button
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekappresensiuser.ResponseRekapPresensi
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import retrofit2.Call
import retrofit2.Response
import java.util.*

@SuppressLint("InflateParams")
class AlertDialog(
    private val context: AppCompatActivity,
    private val username: String?
) {
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_alert_dialog, null)
        val startDate = view.findViewById<TextView>(R.id.dariTanggal)
        val endDate = view.findViewById<TextView>(R.id.sampaiTanggal)
        val perRangeTanggalProses = view.findViewById<Button>(R.id.perRangeTanggalProses)
        val perbulanproses = view.findViewById<Button>(R.id.perbulanproses)
        val bulan = view.findViewById<Spinner>(R.id.bulan)

        val calendar = Calendar.getInstance()
        startDate.setOnClickListener {
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    startDate.setText(
                        context.getString(
                            R.string.date,
                            year,
                            Constant.dateFormat(monthOfYear.plus(1)),
                            Constant.dateFormat(dayOfMonth)
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show(context.supportFragmentManager, context.getString(R.string.dp))
        }

        endDate.setOnClickListener {
            val dpd = DatePickerDialog.newInstance(
                { _, year, monthOfYear, dayOfMonth ->
                    endDate.setText(
                        context.getString(
                            R.string.date,
                            year,
                            Constant.dateFormat(monthOfYear.plus(1)),
                            Constant.dateFormat(dayOfMonth)
                        )
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show(context.supportFragmentManager, context.getString(R.string.dp))
        }

        perRangeTanggalProses.setOnClickListener {
            perRangeTanggalProses.text = "Memproses..."
            perRangeTanggalProses.isEnabled = false
            NetworkModules().getService().rekapPresensiRange(
                startDate = startDate.text.toString(),
                endDate = endDate.text.toString(),
                username = username
            ).enqueue(object : retrofit2.Callback<ResponseRekapPresensi> {
                override fun onFailure(call: Call<ResponseRekapPresensi>, t: Throwable) {
                    perRangeTanggalProses.text = "Kirim"
                    perRangeTanggalProses.isEnabled = true
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()

                }

                override fun onResponse(
                    call: Call<ResponseRekapPresensi>,
                    response: Response<ResponseRekapPresensi>
                ) {
                    perRangeTanggalProses.text = "Kirim"
                    perRangeTanggalProses.isEnabled = true
                    if (response.isSuccessful) {
                        val code = response.body()?.code
                        if (code == 200) {
                            AlertDialog.Builder(context)
                                .setTitle("Jumlah Masuk")
                                .setMessage(response.body()?.jumlah)
                                .setPositiveButton("Okay") { d, _ ->
                                    d.dismiss()
                                }.create().show()
                        } else
                            Toast.makeText(
                                context,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                    } else {
                        Log.d("error", response.errorBody().toString())
                        Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }
        perbulanproses.setOnClickListener {
            perbulanproses.text = "Memproses..."
            perbulanproses.isEnabled = false
            NetworkModules().getService().rekapPresensiBulan(
                bulan = Constant.dateFormat(bulan.selectedItemPosition.plus(1)),
                username = username
            ).enqueue(object : retrofit2.Callback<ResponseRekapPresensi> {
                override fun onFailure(call: Call<ResponseRekapPresensi>, t: Throwable) {
                    perbulanproses.text = "Kirim"
                    perbulanproses.isEnabled = true
                    Toast.makeText(context, t.message.toString(), Toast.LENGTH_SHORT).show()
                }

                override fun onResponse(
                    call: Call<ResponseRekapPresensi>,
                    response: Response<ResponseRekapPresensi>
                ) {
                    perbulanproses.text = "Kirim"
                    perbulanproses.isEnabled = true
                    if (response.isSuccessful) {
                        val code = response.body()?.code
                        if (code == 200) {
                            AlertDialog.Builder(context)
                                .setTitle("Jumlah Masuk")
                                .setMessage(response.body()?.jumlah)
                                .setPositiveButton("Okay") { d, _ ->
                                    d.dismiss()
                                }.create().show()
                        } else
                            Toast.makeText(
                                context,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                    } else {
                        Log.d("error", response.errorBody().toString())
                        Toast.makeText(context, "Something wrong", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

        val alert = AlertDialog.Builder(context)
        alert.setView(view)

        alert.create().show()
    }
}
