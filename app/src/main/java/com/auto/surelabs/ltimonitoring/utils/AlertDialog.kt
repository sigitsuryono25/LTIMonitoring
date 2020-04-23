package com.auto.surelabs.ltimonitoring.utils

import android.app.AlertDialog
import android.view.LayoutInflater
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.ltimonitoring.R
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog
import java.util.*

class AlertDialog(private val context: AppCompatActivity) {
    init {
        val view = LayoutInflater.from(context).inflate(R.layout.fragment_alert_dialog, null)
        val startDate = view.findViewById<EditText>(R.id.dariTanggal)
        val endDate = view.findViewById<EditText>(R.id.sampaiTanggal)

        val calendar = Calendar.getInstance()
        startDate.setOnClickListener {
            val dpd = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth ->
                    startDate.setText(
                        "$year-${String.format(
                            "%02d",
                            monthOfYear
                        )}-${String.format("%02d", dayOfMonth)}"
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show(context.supportFragmentManager, "Datepickerdialog")
        }

        endDate.setOnClickListener {
            val dpd = DatePickerDialog.newInstance(
                { view, year, monthOfYear, dayOfMonth ->
                    endDate.setText(
                        "$year-${String.format(
                            "%02d",
                            monthOfYear
                        )}-${String.format("%02d", dayOfMonth)}"
                    )
                },
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )

            dpd.show(context.supportFragmentManager, "Datepickerdialog")
        }

        val alert = AlertDialog.Builder(context)
        alert.setView(view)

        alert.create().show()
    }
}
