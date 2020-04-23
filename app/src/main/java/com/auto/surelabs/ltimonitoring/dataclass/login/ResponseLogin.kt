package com.auto.surelabs.ltimonitoring.dataclass.login

import com.google.gson.annotations.SerializedName

data class ResponseLogin(

    @field:SerializedName("code")
    val code: Int? = null,

    @field:SerializedName("data")
    val data: Data? = null,

    @field:SerializedName("message")
    val message: String? = null
)

data class Data(

    @field:SerializedName("password")
    val password: String? = null,

    @field:SerializedName("nama")
    val nama: String? = null,

    @field:SerializedName("id_admin")
    val idAdmin: String? = null
)
