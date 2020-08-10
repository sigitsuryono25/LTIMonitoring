package com.auto.surelabs.ltimonitoring.ui.mitra

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.GridLayoutManager
import com.auto.surelabs.ltimonitoring.R
import com.auto.surelabs.ltimonitoring.dataclass.rekap.ResponseRekap
import com.auto.surelabs.ltimonitoring.ui.mitra.adapter.AdapterMitra
import com.auto.surelabs.ltimonitoring.utils.Constant
import kotlinx.android.synthetic.main.rekap_fragment.*
import retrofit2.Response

class MitraFragment : Fragment() {
    private lateinit var viewModel: MitraViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.rekap_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider.NewInstanceFactory().create(MitraViewModel::class.java)
        viewModel.getListMitra("mitra")
        viewModel.success.observe(viewLifecycleOwner, Observer { setToAdapter(it) })
        viewModel.error.observe(viewLifecycleOwner, Observer { setToError(it) })
    }

    private fun setToError(throwable: Throwable?) {
        throwable?.printStackTrace()
    }

    private fun setToAdapter(response: Response<ResponseRekap>?) {
        if (response?.isSuccessful == true) {
            val data = response.body()?.data
            val adapter = AdapterMitra(data) {

            }
            mitra.layoutManager = GridLayoutManager(
                context,
                Constant.calculateNoOfColumns(context!!, 100F)
            )
            mitra.adapter = adapter
            mitra.itemAnimator = DefaultItemAnimator()
            adapter.notifyDataSetChanged()

        }
    }

}