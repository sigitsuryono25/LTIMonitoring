package com.auto.surelabs.ltimonitoring.ui.rekap

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.ui.rekap.adapter.NewAdapterRekap
import kotlinx.android.synthetic.main.fragment_mitra.*
import retrofit2.Response

class RekapFragment : Fragment() {
    private lateinit var model: RekapViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_mitra, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        model = ViewModelProvider.NewInstanceFactory().create(RekapViewModel::class.java)
        model.getListMitra("rekap")
        model.success.observe(viewLifecycleOwner, Observer { setToAdapter(it) })
        model.error.observe(viewLifecycleOwner, Observer { setToError(it) })
    }

    private fun setToError(throwable: Throwable?) {
        Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
        throwable?.printStackTrace()
    }

    private fun setToAdapter(response: Response<ResponseRekap>?) {
        if (response?.isSuccessful == true) {
            val code = response.body()?.code
            if (code == 200) {
                val data = response.body()?.data
                val adapter = NewAdapterRekap(data)
                mitraList.adapter = adapter
                mitraList.itemAnimator = DefaultItemAnimator()
                adapter.notifyDataSetChanged()
            } else {
                Toast.makeText(context, response.body()?.message, Toast.LENGTH_SHORT).show()
            }
        } else {
            Toast.makeText(context, getString(R.string.something_wrong), Toast.LENGTH_SHORT).show()
        }
    }
}