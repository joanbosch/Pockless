package com.pes.pockles.util

import android.app.Activity
import com.google.android.gms.location.LocationServices

class LocationUtils {
    companion object {
        fun getLatLocation(activity: Activity, listener: LastLocationListener) {
            val locationClient = LocationServices.getFusedLocationProviderClient(activity)

            locationClient.lastLocation.addOnSuccessListener { location ->
                if (location != null) listener.onLocationReady(location) else listener.onLocationError(
                    null
                )
            }.addOnFailureListener { e ->
                listener.onLocationError(e)
            }
        }
    }
}

interface LastLocationListener {
    fun onLocationReady(location: android.location.Location)
    fun onLocationError(error: Throwable?)
}