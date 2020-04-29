package com.auto.surelabs.ltimonitoring.dataclass.rekappresensiuser

import com.google.gson.annotations.SerializedName

data class ResponseRekapPresensi(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("jumlah")
    val jumlah: String? = null,

    @field:SerializedName("message")
    val message: String? = null
)