package com.ergo.notch.sbquiz.ui.viewmodel

import android.content.Context
import android.location.Location
import androidx.lifecycle.MutableLiveData
import com.ergo.notch.sbquiz.ui.model.StationEntity
import com.ergo.notch.sbquiz.utils.Helper
import com.ergo.notch.sbquiz.utils.ResponseListener

class StationsLiveData(private val context: Context) : MutableLiveData<List<StationEntity>>() {

    private var bikes: List<StationEntity> = Helper(context).getInfoBikes()
    private val mResponseListener = object : ResponseListener {
        override fun onSuccess(stations: List<StationEntity>) {
            value = stations
        }

        override fun onError() {
            value = arrayListOf()
        }
    }

    fun sortListByLocation(location: Location) {
        Helper(context = context).sortListByLocation(
            location,
            mResponseListener,
            bikes
        )
    }
}