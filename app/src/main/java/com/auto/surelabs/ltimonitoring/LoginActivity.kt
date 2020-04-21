package com.auto.surelabs.ltimonitoring

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_login.*

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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
                doLogin(username.text.toString(), password.text.toString())
            }

        }
    }

    private fun doLogin(username: String?, password: String?) {
        if (username.equals("admin") && password.equals("admin")) {
            finish()
            Intent(this@LoginActivity, MainActivity::class.java).apply {
                startActivity(this)
            }
        } else {
            Toast.makeText(this@LoginActivity, "Uername atau password salah", Toast.LENGTH_SHORT)
                .show()
        }
    }
}
