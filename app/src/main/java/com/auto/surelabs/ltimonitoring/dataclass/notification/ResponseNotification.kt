package com.auto.surelabs.ltimonitoring.dataclass.notification


import com.google.gson.annotations.SerializedName

data class ResponseNotification(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("icon")
    val icon: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("body")
    val body: String? = null,

    @field:SerializedName("channel_id")
    val channelId: String? = null
)