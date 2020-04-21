package com.auto.surelabs.ltimonitoring

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import com.auto.surelabs.ltimonitoring.mitra.MitraKerjaActivity
import com.auto.surelabs.ltimonitoring.pengumuman.PengumumanActivity
import com.auto.surelabs.ltimonitoring.rekap.RekapActivity

class MainActivity : AppCompatActivity() {

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
    }
}
