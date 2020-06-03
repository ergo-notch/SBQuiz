package com.ergo.notch.sbquiz.utils

import android.content.Context
import android.location.Location
import com.ergo.notch.sbquiz.R
import com.ergo.notch.sbquiz.ui.model.StationEntity
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


@Suppress("JavaCollectionsStaticMethodOnImmutableList")
class Helper(private val context: Context) {

    fun getInfoBikes(): List<StationEntity> {
        val jsonBikes = context.resources.openRawResource(R.raw.bikes)
            .bufferedReader().use { it.readText() }

        return try {
            val responseType =
                object : TypeToken<List<StationEntity>>() {}.type
            Gson().fromJson(jsonBikes, responseType)
        } catch (exception: Exception) {
            arrayListOf()
        }

    }

    fun sortListByLocation(
        location: Location,
        mResponseListener: ResponseListener,
        bikes: List<StationEntity>
    ) {
        val result = arrayListOf<StationEntity>()
        bikes.forEach { stationEntity ->
            stationEntity.bikes?.let {
                if (it.toDouble() > 0) {
                    val distance = FloatArray(1)
                    stationEntity.lat?.toDouble()?.let { latitude ->
                        stationEntity.lon?.toDouble()?.let { longitude ->
                            Location.distanceBetween(
                                location.latitude,
                                location.longitude,
                                latitude,
                                longitude,
                                distance
                            )
                        }
                    }
                    stationEntity.distance = distance[0] / 1000
                    result.add(stationEntity)
                }
            }
        }

        result.sortWith(Comparator { p0, p1 ->
            p0.distance.compareTo(p1.distance)
        })
        mResponseListener.onSuccess(result)
    }
}
