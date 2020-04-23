package com.auto.surelabs.ltimonitoring.mitra.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.DataItem
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.item_adapter_mitra.view.*

class AdapterMitra(
    private val listRecap: List<DataItem?>?,
    private val click: (DataItem?) -> Unit
) :
    RecyclerView.Adapter<AdapterMitra.ViewHolder>() {
    class ViewHolder(itemView: View, private val click: (DataItem?) -> Unit) :
        RecyclerView.ViewHolder(itemView) {
        private val foto = itemView.fotoUser
        private val nama = itemView.nama

        fun onBindItem(item: DataItem?) {
            Glide.with(itemView.context)
                .load(item?.foto)
                .apply(RequestOptions.circleCropTransform())
                .into(foto)

            nama.text = item?.nama

            itemView.setOnClickListener {
                click(item)
            }

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_adapter_mitra,
                parent,
                false
            ),
            click
        )
    }

    override fun getItemCount(): Int = listRecap?.size ?: 0

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBindItem(listRecap?.get(position))
    }
}

