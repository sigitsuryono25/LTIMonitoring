package com.auto.surelabs.ltimonitoring.pengumuman

import java.io.Serializable

data class PengumumanItem(
    val id: String? = null,
    val judul: String? = null,
    val isi: String? = null,
    val broadcaston: String? = null,
    val receivedOn: String? = null,
    val isRead: Int? = 0
) : Serializable