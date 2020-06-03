package com.ergo.notch.sbquiz.ui.view.fragments

import android.Manifest
import android.annotation.SuppressLint
import android.location.Location
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.ergo.notch.sbquiz.R
import com.ergo.notch.sbquiz.base.view.BaseFragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.ia.mchaveza.kotlin_library.PermissionCallback
import com.ia.mchaveza.kotlin_library.PermissionManager
import com.ia.mchaveza.kotlin_library.TrackingManager
import com.ia.mchaveza.kotlin_library.TrackingManagerLocationCallback
import kotlinx.android.synthetic.main.fragment_map_layout.*
import kotlin.math.absoluteValue

/**
 * A simple [Fragment] subclass.
 * Use the [MapFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class MapFragment : BaseFragment(), OnMapReadyCallback,
    PermissionCallback,
    TrackingManagerLocationCallback {

    private var mGoogleMap: GoogleMap? = null
    private var currentLocationFounded: Location? = null

    private val trackingManager: TrackingManager by lazy {
        TrackingManager(this.requireContext())
    }

    private val permissionManager by lazy {
        PermissionManager(
            requireActivity(),
            this
        )
    }


    override fun getResourceLayout(): Int = R.layout.fragment_map_layout

    override fun initView(view: View, savedInstanceState: Bundle?) {
        this.mvTripPreview.onCreate(savedInstanceState)
        this.mvTripPreview.getMapAsync(this)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        this.mvTripPreview.onSaveInstanceState(outState)
    }

    override fun onMapReady(p0: GoogleMap?) {
        this.mGoogleMap = p0
        this.setCurrentLocationOnMap()
    }

    private fun setCurrentLocationOnMap() {
        if (!this.permissionManager.requestSinglePermission(Manifest.permission.ACCESS_FINE_LOCATION)) {
            this.startLocating()
        }
    }

    @SuppressLint("MissingPermission")
    private fun startLocating() {
        this.mGoogleMap?.isMyLocationEnabled = true
        this.mGoogleMap?.uiSettings?.isMyLocationButtonEnabled = true
//        if (this.trackingManager.areLocationServicesEnabled()) {
        this.trackingManager.startLocationUpdates(
            this,
            5000,
            5000
        )
//        }
    }

    override fun onPermissionDenied(permission: String) {
        this.showAlertWithAction(this.getString(R.string.message_permission_denied)) {
            this.setCurrentLocationOnMap()
        }
    }

    override fun onPermissionGranted(permission: String) {
        this.startLocating()
    }

    override fun onLocationHasChanged(location: Location) {
        this.currentLocationFounded?.let {
                this.addMarkerToMap(location)
        } ?: kotlin.run {
            this.addMarkerToMap(location)
        }
        this.currentLocationFounded = location
    }

    private fun addMarkerToMap(location: Location) {
        this.mGoogleMap?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(location.latitude, location.longitude),
                18f
            )
        )
        this.mGoogleMap?.animateCamera(CameraUpdateFactory.zoomTo(15f))
    }

    override fun onLocationHasChangedError(error: Exception) {
        this.trackingManager.stopLocationUpdates()
    }

    override fun onResume() {
        super.onResume()
        this.mvTripPreview?.onResume()
    }

    override fun onPause() {
        super.onPause()
        this.mvTripPreview?.onPause()
    }

    override fun onStart() {
        super.onStart()
        this.mvTripPreview?.onStart()
    }

    override fun onStop() {
        super.onStop()
        this.mvTripPreview?.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        this.mvTripPreview?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        this.mvTripPreview?.onLowMemory()
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         */
        @JvmStatic
        fun newInstance() =
            MapFragment()
    }

}
