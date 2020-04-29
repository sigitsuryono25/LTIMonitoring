package com.auto.surelabs.ltimonitoring.dataclass.rekappresensiuser

import com.google.gson.annotations.SerializedName

data class DataItem(

    @field:SerializedName("presensi")
    val presensi: String? = null
)