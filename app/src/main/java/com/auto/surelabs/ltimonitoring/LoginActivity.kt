package com.auto.surelabs.ltimonitoring

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.ltimonitoring.dataclass.admin.Admin
import com.auto.surelabs.ltimonitoring.dataclass.ipaddress.ResponseIp
import com.auto.surelabs.ltimonitoring.dataclass.login.ResponseLogin
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Response

class LoginActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        preferences = getSharedPreferences(application.packageName, Context.MODE_PRIVATE)
        if (preferences.contains("idAdmin")) {
            Intent(this@LoginActivity, MainActivity::class.java).apply {
                startActivity(this)
            }
            finish()
        }
        setContentView(R.layout.activity_login)

        btnLogin.setOnClickListener {
            if (username.text?.isEmpty() == true || password.text?.isEmpty() == true) {
                AlertDialog.Builder(this@LoginActivity)
                    .setMessage("Silahkan Isi Semua kolom yang tersedia")
                    .setTitle("Pesan")
                    .setPositiveButton("Okay") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .create()
                    .show()
            } else {
                btnLogin.isEnabled = false
                btnLogin.text = "proses..."
                doLogin(username.text.toString(), password.text.toString())
            }

        }

    }

    private fun doLogin(username: String?, password: String?) {
        if (username?.isEmpty() == false && password?.isEmpty() == false) {
            NetworkModules().getService().getIPAddress()
                .enqueue(object : retrofit2.Callback<ResponseIp> {
                    override fun onFailure(call: Call<ResponseIp>, t: Throwable) {
                        btnLogin.isEnabled = true
                        btnLogin.text = "Login"
                        Toast.makeText(this@LoginActivity, "Something wrong", Toast.LENGTH_SHORT)
                            .show()
                    }

                    override fun onResponse(
                        call: Call<ResponseIp>,
                        response: Response<ResponseIp>
                    ) {
                        getUserDetail(username, password, response.body())
                    }

                })
        } else {
            Toast.makeText(this@LoginActivity, "Uername atau password salah", Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun getUserDetail(username: String, password: String, body: ResponseIp?) {
        val admin = Admin()
        admin.username = username
        admin.password = password
        admin.city = body?.city
        admin.country = body?.country
        admin.countryCode = body?.countryCode
        admin.ip = body?.query
        admin.isp = body?.isp
        admin.lat = body?.lat
        admin.lon = body?.lon
        admin.timezone = body?.timezone
        admin.userAgent =
            "Android/${Build.DEVICE}/${Build.MODEL}/${Build.PRODUCT}/${Build.FINGERPRINT}/${Build.VERSION.SDK_INT}"

        NetworkModules().getService().doLoginAdmin(
            admin
        ).enqueue(object : retrofit2.Callback<ResponseLogin> {
            override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                Log.e("ERROR", t.message.toString())
            }

            override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                val code = response.body()?.code
                if (code == 200) {
                    preferences.edit().apply {
                        this.putString("nama", response.body()?.data?.nama)
                        this.putString("idAdmin", response.body()?.data?.idAdmin)
                    }.apply()

                    btnLogin.isEnabled = true
                    btnLogin.text = "Login"
                    Intent(this@LoginActivity, MainActivity::class.java).apply {
                        startActivity(this)
                    }
                } else {
                    Toast.makeText(this@LoginActivity, "Login gagal", Toast.LENGTH_SHORT).show()
                }
            }

        })
    }
}
