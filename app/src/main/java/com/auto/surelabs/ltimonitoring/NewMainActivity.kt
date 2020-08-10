package com.auto.surelabs.ltimonitoring

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.auto.surelabs.ltimonitoring.ui.dashboard.DashboardFragment
import com.auto.surelabs.ltimonitoring.ui.mitra.MitraFragment
import com.auto.surelabs.ltimonitoring.ui.pengaturan.SettingFragment
import com.auto.surelabs.ltimonitoring.ui.pengumuman.PengumumanFragment
import com.auto.surelabs.ltimonitoring.ui.rekap.RekapFragment
import kotlinx.android.synthetic.main.activity_new_main.*

class NewMainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_main)

        val from = intent.getStringExtra("data")
        if (from == "notif") {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, RekapFragment()).commitNow()
            bottom_nav.selectedItemId = R.id.rekap
        } else {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container, DashboardFragment()).commitNow()
        }

        bottom_nav.setOnNavigationItemSelectedListener {
            when (it.itemId) {
                R.id.dashboard -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, DashboardFragment()).commitNow()
                    supportActionBar?.title = getString(R.string.dashboard)
                    return@setOnNavigationItemSelectedListener true
                }

                R.id.mitra -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, MitraFragment()).commitNow()
                    supportActionBar?.title = getString(R.string.mitra_kerja)

                    return@setOnNavigationItemSelectedListener true
                }

                R.id.rekap -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, RekapFragment()).commitNow()
                    supportActionBar?.title = getString(R.string.rekap_presensi)

                    return@setOnNavigationItemSelectedListener true
                }

                R.id.settings -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, SettingFragment()).commitNow()
                    supportActionBar?.title = getString(R.string.settings)

                    return@setOnNavigationItemSelectedListener true
                }

                R.id.pengumuman -> {
                    supportFragmentManager.beginTransaction()
                        .replace(R.id.container, PengumumanFragment()).commitNow()
                    supportActionBar?.title = getString(R.string.pengumuman)

                    return@setOnNavigationItemSelectedListener true
                }
            }
            return@setOnNavigationItemSelectedListener false
        }
    }
}