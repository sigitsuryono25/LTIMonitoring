package com.auto.surelabs.ltimonitoring.ui.dashboard

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.ui.dashboard.adapter.AdapterLogPresensi
import com.auto.surelabs.ltimonitoring.ui.mitra.adapter.AdapterMitra
import com.auto.surelabs.ltimonitoring.utils.HourToMillis
import kotlinx.android.synthetic.main.fragment_dashboard.*
import retrofit2.Response


class DashboardFragment : Fragment() {

    private lateinit var dashboardViewModel: DashboardViewModel
    private lateinit var preferences: SharedPreferences


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_dashboard, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        dashboardViewModel =
            ViewModelProvider.NewInstanceFactory().create(DashboardViewModel::class.java)

        //getOnlineMitra
        dashboardViewModel.getMitraOnline(
            HourToMillis.millisToDate(
                HourToMillis.millis(),
                "yyyy-MM-dd"
            )
        )
        dashboardViewModel.success.observe(
            viewLifecycleOwner,
            Observer { setToAdapterMitraOnline(it) })
        dashboardViewModel.error.observe(viewLifecycleOwner, Observer { setToError(it) })

        //getLogPresensi
//        dashboardViewModel.getLogPresensi()
//        dashboardViewModel.successLog.observe(
//            viewLifecycleOwner,
//            Observer { setToLogPresensi(it) })
//        dashboardViewModel.errorLog.observe(viewLifecycleOwner, Observer { setToError(it) })

        //getLiburMitra
        dashboardViewModel.getLiburMitra()
        dashboardViewModel.successLibur.observe(
            viewLifecycleOwner,
            Observer { setToLiburMitra(it) })
        dashboardViewModel.errorLibur.observe(viewLifecycleOwner, Observer { setToError(it) })

//        initview
        initView()
    }

    private fun setToLiburMitra(response: Response<ResponseRekap>?) {
        if (response?.isSuccessful == true) {
            val code = response.body()?.code
            if (code == 200) {
                val data = response.body()?.data
                val adapter = AdapterLogPresensi(data)
                liburMitra.itemAnimator = DefaultItemAnimator()
                liburMitra.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                liburMitra.adapter = adapter
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    private fun setToLogPresensi(response: Response<ResponseRekap>?) {
        if (response?.isSuccessful == true) {
            val code = response.body()?.code
            if (code == 200) {
                val data = response.body()?.data
                val adapter = AdapterLogPresensi(data)
                logPresensi.itemAnimator = DefaultItemAnimator()
                logPresensi.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
                logPresensi.adapter = adapter
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
        }
    }

    private fun initView() {
        val date = HourToMillis.millisToDate(HourToMillis.millis(), "E, dd/MM/yyyy")
        tanggalToday.text = date

        preferences =
            context!!.getSharedPreferences(activity!!.application.packageName, Context.MODE_PRIVATE)
        val welcomeString = preferences.getString("nama", null)
        welcome.text = context?.getString(R.string.welcome_administrator, welcomeString)
    }

    private fun setToError(it: Throwable?) {
        it?.printStackTrace()
    }

    private fun setToAdapterMitraOnline(response: Response<ResponseRekap>?) {
        if (response?.isSuccessful == true) {
            val code = response.body()?.code
            if (code == 200) {
                val data = response.body()?.data
                val adapter = AdapterMitra(data) {

                }
                onlineMitra.itemAnimator = DefaultItemAnimator()
                onlineMitra.layoutManager =
                    LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                onlineMitra.adapter = adapter
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
        }
    }
}