package com.auto.surelabs.ltimonitoring.dataclass.rekap

import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("pulang")
    val pulang: String? = null,

    @field:SerializedName("keterangan")
    val keterangan: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("foto")
    val foto: String? = null,

    @field:SerializedName("masuk")
    val masuk: String? = null,

    @field:SerializedName("username")
    val username: String? = null,

    @field:SerializedName("tanggal")
    val tanggal: String? = null,

    @field:SerializedName("status")
    val status: String? = null,

    @field:SerializedName("libur")
    val libur: String? = null
)