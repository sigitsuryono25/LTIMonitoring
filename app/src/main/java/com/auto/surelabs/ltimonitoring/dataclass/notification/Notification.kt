package com.auto.surelabs.ltimonitoring.dataclass.notification

import com.auto.surelabs.ltimonitoring.pengumuman.PengumumanItem
import com.google.gson.annotations.SerializedName

class Notification {
    @SerializedName("to")
    var token: String? = null
    @SerializedName("data")
    var body: PengumumanItem? = null
}