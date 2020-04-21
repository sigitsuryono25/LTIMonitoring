package com.auto.surelabs.ltimonitoring.rekap.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_adapter_rekap.view.*

/**
 * A simple [Fragment] subclass.
 */
class AdapterRekap(private val listRecap: List<DataItem?>?) :
    RecyclerView.Adapter<AdapterRekap.ViewHolder>() {
    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val foto = itemView.fotoUser
        private val nama = itemView.nama
        private val masuk = itemView.masuk
        private val pulang = itemView.pulang
        private val keterangan = itemView.keterangan

        fun onBindItem(item: DataItem?) {
            Glide.with(itemView.context)
                .load(item?.foto)
                .apply(RequestOptions.circleCropTransform())
                .into(foto)
            if (item?.pulang?.isNotEmpty() == true) {
                pulang.text = item.pulang
                pulang.visibility = View.VISIBLE

            }
            if (item?.masuk?.isNotEmpty() == true) {
                masuk.text = item.masuk
                masuk.visibility = View.VISIBLE
            }
            nama.text = item?.nama
            if (item?.keterangan?.isNotEmpty() == true) {
                keterangan.text = item.keterangan
                keterangan.visibility = View.VISIBLE
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_adapter_rekap,
                parent,
                false
            )
        )
    }

    override fun getItemCount(): Int = listRecap?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listRecap?.get(position))
    }

}