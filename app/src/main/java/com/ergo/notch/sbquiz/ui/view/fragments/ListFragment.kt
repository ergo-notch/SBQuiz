package com.ergo.notch.sbquiz.ui.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.ergo.notch.sbquiz.R
import com.ergo.notch.sbquiz.base.view.BaseFragment
import com.ergo.notch.sbquiz.ui.model.StationEntity
import com.ergo.notch.sbquiz.ui.view.adapters.ListFragmentAdapter
import com.ergo.notch.sbquiz.ui.viewmodel.MainViewModel
import kotlinx.android.synthetic.main.fragment_list_layout.*

/**
 * A simple [Fragment] subclass.
 * Use the [ListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class ListFragment : BaseFragment() {

    private lateinit var mainViewModel: MainViewModel

    private var stationList: List<StationEntity>? = null

    override fun getResourceLayout(): Int = R.layout.fragment_list_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        mainViewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        mainViewModel.getLocationData()
        this.loadAdapter()
        this.observeResults()
    }

    private fun observeResults() {
        mainViewModel.getLocationData().observe(this, Observer {
            mainViewModel.getStationList(it)
        })
        mainViewModel.stationsData.observe(this, Observer {
            this.stationList = it
            this.loadAdapter()
        })
    }

    private fun loadAdapter() {
        this.stationList?.let {
            val adapter = ListFragmentAdapter(it)
            this.rvStations.adapter = adapter
            this.rvStations.layoutManager = LinearLayoutManager(requireContext())
        }

    }


    companion object {
        @JvmStatic
        fun newInstance() = ListFragment()
    }


}