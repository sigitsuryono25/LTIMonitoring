package com.auto.surelabs.ltimonitoring.ui.dashboard.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.DataItem
import kotlinx.android.synthetic.main.item_adapter_log_presensi.view.*

class AdapterLogPresensi(private val mListLog: List<DataItem?>?) :
    RecyclerView.Adapter<AdapterLogPresensi.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val namaMitra = itemView.namaMitra
        private val tanggal = itemView.tanggal
        private val status = itemView.status
        private val libur = itemView.libur

        fun onBind(dataItem: DataItem?) {
            namaMitra.text = dataItem?.nama
            tanggal.text = dataItem?.tanggal
            if (dataItem?.status.equals("masuk")) {
                status.background = itemView.context.getDrawable(R.drawable.green)
            } else if (dataItem?.status.equals("pulang")) {
                status.background = itemView.context.getDrawable(R.drawable.red)
            }
            libur.text = dataItem?.libur
            status.text = dataItem?.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_adapter_log_presensi, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mListLog?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(mListLog?.get(position))
    }

}