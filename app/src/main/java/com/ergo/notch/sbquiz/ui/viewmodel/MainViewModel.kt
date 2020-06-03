package com.ergo.notch.sbquiz.ui.viewmodel

import android.app.Application
import android.location.Location
import androidx.lifecycle.AndroidViewModel

class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val locationData = LocationLiveData(application)

    fun getLocationData() = locationData

    val stationsData = StationsLiveData(application)

    fun getStationList(location: Location) = stationsData.sortListByLocation(location)
}