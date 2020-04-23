package com.auto.surelabs.ltimonitoring.mitra

import android.os.Bundle
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.mitra.adapter.AdapterMitra
import com.auto.surelabs.ltimonitoring.network.NetworkModules
import com.auto.surelabs.ltimonitoring.utils.AlertDialog
import com.auto.surelabs.ltimonitoring.utils.Constant
import kotlinx.android.synthetic.main.activity_mitra_kerja.*
import retrofit2.Call
import retrofit2.Response

class MitraKerjaActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mitra_kerja)
        supportActionBar?.title = getString(R.string.mitra_kerja)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        getListMitra()

        val swipeLayout = findViewById<SwipeRefreshLayout>(R.id.swipeLayout)
        swipeLayout.setOnRefreshListener {
            swipeLayout.isRefreshing = true
            getListMitra()
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun getListMitra() {
        NetworkModules().getService().getListMitra()
            .enqueue(object : retrofit2.Callback<ResponseRekap> {
                override fun onFailure(call: Call<ResponseRekap>, t: Throwable) {
                    if (swipeLayout.isRefreshing)
                        swipeLayout.isRefreshing = false
                    Toast.makeText(
                        this@MitraKerjaActivity,
                        t.message.toString(),
                        Toast.LENGTH_SHORT
                    ).show()
                }

                override fun onResponse(
                    call: Call<ResponseRekap>,
                    response: Response<ResponseRekap>
                ) {
                    if (swipeLayout.isRefreshing)
                        swipeLayout.isRefreshing = false

                    if (response.isSuccessful) {
                        val code = response.body()?.code
                        if (code == 200) {
                            val rvMitra =
                                this@MitraKerjaActivity.findViewById<RecyclerView>(R.id.rvMitra)
                            val data = response.body()?.data
                            val adapter = AdapterMitra(data) {
                                AlertDialog(this@MitraKerjaActivity)
                            }
                            rvMitra.layoutManager = GridLayoutManager(
                                this@MitraKerjaActivity,
                                Constant.calculateNoOfColumns(this@MitraKerjaActivity, 100F)
                            )
                            rvMitra.itemAnimator = DefaultItemAnimator()
                            rvMitra.adapter = adapter
                            adapter.notifyDataSetChanged()
                        } else {
                            Toast.makeText(
                                this@MitraKerjaActivity,
                                response.body()?.message,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    } else {
                        Toast.makeText(
                            this@MitraKerjaActivity,
                            response.errorBody()?.toString(),
                            Toast.LENGTH_SHORT
                        ).show()
                    }

                }

            })
    }

}
