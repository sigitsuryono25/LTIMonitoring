package com.auto.surelabs.ltimonitoring.repo

import com.auto.surelabs.ltimonitoring.dataclass.admin.Admin
import com.auto.surelabs.ltimonitoring.dataclass.ipaddress.ResponseIp
import com.auto.surelabs.ltimonitoring.dataclass.login.ResponseLogin
import com.auto.surelabs.ltimonitoring.dataclass.notification.Notification
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.dataclass.rekappresensiuser.ResponseRekapPresensi
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Response

class Repo {

    private val api = NetworkModules().getService()
    private val apiFcm = NetworkModules().getFcmService()

    fun getListMitra(
        by: String?,
        success: (Response<ResponseRekap>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        api.getListMitra(by).enqueue(object : retrofit2.Callback<ResponseRekap> {
            override fun onFailure(call: Call<ResponseRekap>, t: Throwable) {
                error(t)
            }

            override fun onResponse(call: Call<ResponseRekap>, response: Response<ResponseRekap>) {
                success(response)
            }

        })
    }

    fun getDetailRecap(
        date: String?,
        success: (Response<ResponseRekap>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        api.getDetailRecap(date).enqueue(object : retrofit2.Callback<ResponseRekap> {
            override fun onFailure(call: Call<ResponseRekap>, t: Throwable) {
                error(t)
            }

            override fun onResponse(call: Call<ResponseRekap>, response: Response<ResponseRekap>) {
                success(response)
            }

        })
    }

    fun actionSendService(
        notification: Notification,
        success: (Response<ResponseBody>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        apiFcm.actionSendService(notification).enqueue(object : retrofit2.Callback<ResponseBody> {
            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                error(t)
            }

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                success(response)
            }

        })
    }

    fun getIPAddress(success: (Response<ResponseIp>) -> Unit, error: (Throwable) -> Unit) {
        api.getIPAddress().enqueue(object : retrofit2.Callback<ResponseIp> {
            override fun onFailure(call: Call<ResponseIp>, t: Throwable) {
                error(t)
            }

            override fun onResponse(call: Call<ResponseIp>, response: Response<ResponseIp>) {
                success(response)
            }

        })
    }

    fun doLoginAdmin(
        admin: Admin,
        success: (Response<ResponseLogin>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        api.doLoginAdmin(admin).enqueue(object : retrofit2.Callback<ResponseLogin> {
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                error(t)
            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                success(response)
            }

        })
    }

    fun rekapPresensiRange(
        startDate: String?,
        endDate: String?,
        username: String?,
        success: (Response<ResponseRekapPresensi>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        api.rekapPresensiRange(startDate, endDate, username)
            .enqueue(object : retrofit2.Callback<ResponseRekapPresensi> {
                override fun onFailure(call: Call<ResponseRekapPresensi>, t: Throwable) {
                    error(t)
                }

                override fun onResponse(
                    call: Call<ResponseRekapPresensi>,
                    response: Response<ResponseRekapPresensi>
                ) {
                    success(response)
                }

            })
    }

    fun rekapPresensiBulan(
        bulan: String?,
        username: String?,
        success: (Response<ResponseRekapPresensi>) -> Unit,
        error: (Throwable) -> Unit
    ) {
        api.rekapPresensiBulan(bulan, username)
            .enqueue(object : retrofit2.Callback<ResponseRekapPresensi> {
                override fun onFailure(call: Call<ResponseRekapPresensi>, t: Throwable) {
                    error(t)
                }

                override fun onResponse(
                    call: Call<ResponseRekapPresensi>,
                    response: Response<ResponseRekapPresensi>
                ) {
                    success(response)
                }

            })
    }
}