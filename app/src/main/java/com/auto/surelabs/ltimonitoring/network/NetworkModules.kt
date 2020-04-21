package com.auto.surelabs.ltimonitoring.network

import com.auto.surelabs.ltimonitoring.dataclass.notification.Notification
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

class NetworkModules() {
    private val bASEURL = "https://www.lauwba.com/411/index.php/"
    private val BASEURLFCM = "https://fcm.googleapis.com/"

    private fun getOkHttp(): OkHttpClient {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
            .setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()
    }

    private fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(bASEURL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()
    }

    private fun getRetrofitFcm(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASEURLFCM)
            .addConverterFactory(GsonConverterFactory.create())
            .client(getOkHttp())
            .build()

    }

    fun getService(): ApiService {
        return getRetrofit().create<ApiService>(ApiService::class.java)
    }

    fun getFcmService(): ApiService {
        return getRetrofitFcm().create<ApiService>(ApiService::class.java)
    }

    interface ApiService {
        @GET("Presensi/get_rekap_user")
        fun getDetailRecap(@Query("date") date: String?): retrofit2.Call<ResponseRekap>

        @GET("Presensi/get_list_mitra")
        fun getListMitra(): retrofit2.Call<ResponseRekap>

        @Headers(
            "Content-Type:application/json",
            "Authorization:key=AAAAPwOYugg:APA91bHanvk13CjC9w9vcswV4UvCdSjQh-iIEOTSsA1Ia2TMTlmKuCGGegDKupos6QUXGwxST3Ihnha3XUQLN4IE7IlbLiZsHcJAKyXbW4eTxR4pzVM4or48uUKGCGCZWb-Ib7ym6e4C"
        )
        @POST("fcm/send")
        fun actionSendService(@Body notificationService: Notification): retrofit2.Call<ResponseBody>
    }
}