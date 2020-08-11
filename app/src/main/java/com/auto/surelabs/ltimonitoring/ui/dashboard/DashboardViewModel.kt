package com.auto.surelabs.ltimonitoring.ui.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.repo.Repo
import retrofit2.Response

class DashboardViewModel : ViewModel() {

    val success: MutableLiveData<Response<ResponseRekap>> = MutableLiveData()
    val successLog: MutableLiveData<Response<ResponseRekap>> = MutableLiveData()
    val successLibur: MutableLiveData<Response<ResponseRekap>> = MutableLiveData()
    val error: MutableLiveData<Throwable> = MutableLiveData()
    val errorLog: MutableLiveData<Throwable> = MutableLiveData()
    val errorLibur: MutableLiveData<Throwable> = MutableLiveData()
    private val repo = Repo()

    fun getMitraOnline(date: String?) {
        repo.getOnlineMitra(date, {
            success.value = it
        }, {
            error.value = it
        })
    }

    fun getLogPresensi() {
        repo.getLogPresensi({
            successLog.value = it
        }, {
            errorLog.value = it
        })
    }

    fun getLiburMitra() {
        repo.getLiburMitra({
            successLibur.value = it
        }, {
            errorLibur.value = it
        })
    }
}