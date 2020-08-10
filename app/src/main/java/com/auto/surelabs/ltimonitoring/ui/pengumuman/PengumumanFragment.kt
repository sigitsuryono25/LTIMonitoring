package com.auto.surelabs.ltimonitoring.ui.pengumuman

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.auto.surelabs.ltimonitoring.R

class PengumumanFragment : Fragment() {

    companion object {
        fun newInstance() = PengumumanFragment()
    }

    private lateinit var viewModel: PengumumanViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.pengumuman_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(this).get(PengumumanViewModel::class.java)
        // TODO: Use the ViewModel
    }

}