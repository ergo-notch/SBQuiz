package com.ergo.notch.sbquiz.utils

import com.ergo.notch.sbquiz.ui.model.StationEntity

interface ResponseListener {
    fun onSuccess(stations: List<StationEntity>)
    fun onError()
}