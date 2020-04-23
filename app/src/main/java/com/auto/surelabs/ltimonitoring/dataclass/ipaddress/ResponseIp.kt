package com.auto.surelabs.ltimonitoring.dataclass.ipaddress

import com.google.gson.annotations.SerializedName

data class ResponseIp(

    @field:SerializedName("zip")
    val zip: String? = null,

    @field:SerializedName("country")
    val country: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("org")
    val org: String? = null,

    @field:SerializedName("timezone")
    val timezone: String? = null,

    @field:SerializedName("regionName")
    val regionName: String? = null,

    @field:SerializedName("isp")
    val isp: String? = null,

    @field:SerializedName("query")
    val query: String? = null,

    @field:SerializedName("lon")
    val lon: Double? = null,

    @field:SerializedName("countryCode")
    val countryCode: String? = null,

    @field:SerializedName("region")
    val region: String? = null,

    @field:SerializedName("lat")
    val lat: Double? = null,

    @field:SerializedName("status")
    val status: String? = null
)
