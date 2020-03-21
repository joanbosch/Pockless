package com.pes.pockles.view.ui


import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.NonNull
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.pes.pockles.R


/**
 * A [Fragment] subclass for map view.
 */
class MapFragment : Fragment() , OnMapReadyCallback {
   @Nullable private var mMap: GoogleMap?=null
    private lateinit var lastLocation: Location
    private lateinit var fusedLocationClient: FusedLocationProviderClient



    /*   override fun onCreateView(
           inflater: LayoutInflater, container: ViewGroup?,
           savedInstanceState: Bundle?
       ): View? {
           initMap()

           return inflater.inflate(R.layout.fragment_map, container, false)
       }*/

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView: View =
            inflater.inflate(R.layout.fragment_map, container, false)
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        return rootView
    }
/*
    private fun initMap(){
        if(mMap==null) {
            val mapFragment: SupportMapFragment? =
                childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
            mapFragment?.getMapAsync(this)
        }
    }*/
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val loctest = LatLng(-30.0, 151.0)
        mMap!!.addMarker(MarkerOptions().position(loctest).title("test"))
      //  mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))
       setUpMap(mMap!!)
    }

/* Permission and location management **/

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }



    private fun setUpMap( googleMap: GoogleMap) {
        mMap = googleMap
            if (context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.ACCESS_FINE_LOCATION)
                } != PackageManager.PERMISSION_GRANTED &&
                context?.let {
                    ActivityCompat.checkSelfPermission(
                        it,
                        android.Manifest.permission.ACCESS_COARSE_LOCATION)
                } != PackageManager.PERMISSION_GRANTED) {
                this.activity?.let {
                    ActivityCompat.requestPermissions(
                        it,
                        arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION), LOCATION_PERMISSION_REQUEST_CODE)
                }; // return
                mMap!!.isMyLocationEnabled = true


                getLastLocation()

                        val currentLatLng = LatLng(lastLocation.latitude, lastLocation.longitude)
                        mMap!!.animateCamera(CameraUpdateFactory.newLatLngZoom(currentLatLng, 12f))
                    }
                }
    private fun getLastLocation() {
        val locationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        try
        {
            locationClient.lastLocation
                .addOnSuccessListener { location -> // GPS location can be null if GPS is switched off
                    if (location != null) {
                        if (mMap != null) {
                            mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(location.latitude, location.longitude), 15f))
                        }
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        }
        catch (e:SecurityException) {
            e.printStackTrace()
        }
    }
}
