package com.auto.surelabs.ltimonitoring.dataclass.rekap

import com.google.gson.annotations.SerializedName

data class ResponseRekap(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: List<DataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null
)