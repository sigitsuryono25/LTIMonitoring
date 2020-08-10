package com.auto.surelabs.ltimonitoring

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.messaging.FirebaseMessaging

class SplashScreenActivity : AppCompatActivity() {

    private val h = Handler()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        FirebaseMessaging.getInstance().subscribeToTopic("/topics/presensi").addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("Topics", "Successful subscribe")
            }
        }

        h.postDelayed({
            startActivity(Intent(this@SplashScreenActivity, NewMainActivity::class.java))
        }, 5000)
    }


    override fun onStop() {
        super.onStop()
        h.removeCallbacksAndMessages(null)
    }

    override fun onPause() {
        super.onPause()
        h.removeCallbacksAndMessages(null)
    }

}