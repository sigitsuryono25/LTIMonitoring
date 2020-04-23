package com.auto.surelabs.ltimonitoring

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.auto.surelabs.ltimonitoring.mitra.MitraKerjaActivity
import com.auto.surelabs.ltimonitoring.pengumuman.PengumumanActivity
import com.auto.surelabs.ltimonitoring.rekap.RekapActivity

class MainActivity : AppCompatActivity() {

    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recap = findViewById<CardView>(R.id.recap)
        recap.setOnClickListener {
            Intent(this@MainActivity, RekapActivity::class.java).apply {
                startActivity(this)
            }
        }

        val mitra = findViewById<CardView>(R.id.mitra)
        mitra.setOnClickListener {
            Intent(this@MainActivity, MitraKerjaActivity::class.java).apply {
                startActivity(this)
            }
        }

        val pengumuman = findViewById<CardView>(R.id.pengumuman)
        pengumuman.setOnClickListener {
            Intent(this@MainActivity, PengumumanActivity::class.java).apply {
                startActivity(this)
            }
        }

        preferences = getSharedPreferences(application.packageName, Context.MODE_PRIVATE)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.logout -> {
                preferences.edit().clear().apply()
                Intent(this@MainActivity, LoginActivity::class.java).apply { startActivity(this) }
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
