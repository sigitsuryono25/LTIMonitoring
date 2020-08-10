package com.auto.surelabs.ltimonitoring.ui.rekap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.RecyclerView
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.progress.ResponseProgress
import com.auto.surelabs.ltimonitoring.dataclass.rekap.DataItem
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import com.auto.surelabs.ltimonitoring.utils.HourToMillis
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_adapter_rekap.view.fotoUser
import kotlinx.android.synthetic.main.item_adapter_rekap.view.masuk
import kotlinx.android.synthetic.main.item_adapter_rekap.view.nama
import kotlinx.android.synthetic.main.item_adapter_rekap.view.pulang
import kotlinx.android.synthetic.main.item_new_adapter_mitra.view.*
import retrofit2.Call
import retrofit2.Response

class NewAdapterRekap(
    private val mLIstMitra: List<DataItem?>?
) : RecyclerView.Adapter<NewAdapterRekap.ViewHolder>() {
    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val fotoUser = itemView.fotoUser
        private val nama = itemView.nama
        private val masuk = itemView.masuk
        private val pulang = itemView.pulang
        private val kegiatanHarian = itemView.progress

        fun onBindItem(dataItem: DataItem?) {
            Glide.with(itemView.context).load(dataItem?.foto)
                .apply(RequestOptions.circleCropTransform()).into(fotoUser)
            nama.text = dataItem?.nama
            masuk.text = dataItem?.masuk
            pulang.text = dataItem?.pulang
            getKegiatan(dataItem?.username)
        }

        private fun getKegiatan(username: String?) {
            NetworkModules().getService().getProgress(
                username,
                HourToMillis.millisToDate(HourToMillis.millis(), "dd/MM/YYYY")
            )
                .enqueue(object : retrofit2.Callback<ResponseProgress> {
                    override fun onFailure(call: Call<ResponseProgress>, t: Throwable) {
                        t.printStackTrace()
                    }

                    override fun onResponse(
                        call: Call<ResponseProgress>,
                        response: Response<ResponseProgress>
                    ) {
                        if (response.isSuccessful) {
                            val code = response.body()?.code
                            if (code == 200) {
                                val msg = response.body()?.message
                                kegiatanHarian.text = HtmlCompat.fromHtml(
                                    msg.toString(),
                                    HtmlCompat.FROM_HTML_MODE_LEGACY
                                )
                            } else {
                                kegiatanHarian.text = response.body()?.message.toString()
                            }
                        } else {
                            print(itemView.context.getString(R.string.something_wrong))
                        }
                    }

                })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_new_adapter_mitra, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return mLIstMitra?.size ?: 0
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(mLIstMitra?.get(position))
    }

}