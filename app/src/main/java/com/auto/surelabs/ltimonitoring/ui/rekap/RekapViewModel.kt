package com.auto.surelabs.ltimonitoring.ui.rekap

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.repo.Repo
import retrofit2.Response

class RekapViewModel : ViewModel() {

    val success: MutableLiveData<Response<ResponseRekap>> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    private val repo = Repo()

    fun getListMitra(by: String? = "mitra") {
        repo.getListMitra(by, {
            success.value = it
        }, {
            error.value = it
        })
    }
}