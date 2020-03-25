package com.pes.pockles.view.ui.map


import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Looper
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.FragmentMapBinding
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock
import com.pes.pockles.view.viewmodel.ViewModelFactory


/**
 * A [Fragment] subclass for map view.
 */
open class MapFragment : Fragment(), OnMapReadyCallback {
    @Nullable
    private var mMap: GoogleMap? = null
    private lateinit var lastLocation: android.location.Location
    private lateinit var pocklesLocation: Location
    private lateinit var locationCallback: LocationCallback
    private var mFusedLocationProviderClient: FusedLocationProviderClient? = null
    private val INTERVAL: Long = 2000
    private val FASTEST_INTERVAL: Long = 1000
    private lateinit var mLocationRequest: LocationRequest
    private val REQUEST_PERMISSION_LOCATION = 10
    private lateinit var binding: FragmentMapBinding
    private val viewModel: MapViewModel by lazy {
        ViewModelProviders.of(this, ViewModelFactory()).get(MapViewModel::class.java)
    }

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


        // binding = DataBindingUtil.inflate(    //CRASH
        //      inflater, R.layout.fragment_map, container, false
        //  );
        //   binding.lifecycleOwner = this
        //  binding.MapViewModel = viewModel
        /* TODO
        viewModel.networkCallback?.observe(
            this,
            Observer { value: Resource<Pock>? ->
                value?.let {
                    when (value) {
                        is Resource.Success<*> -> handleSuccess()
                        is Resource.Error -> handleError(value.exception)
                    }
                }
            }) */
        //init map
        val mapFragment =
            childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        mLocationRequest = LocationRequest.create()

        //gps deactivated message
/*  val locationManager = getSystemService(Context.LOCATION_SERVICE) as LocationManager
        if (!locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            buildAlertMessageNoGps()
        }*/
        return rootView
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        /* I will leave this commented just in case i need this later
      *    val loctest = LatLng(-30.0, 151.0)
      *    mMap!!.addMarker(MarkerOptions().position(loctest).title("test"))
      *    mMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(sydney, 12.0f))
      *
      */
        setUpMap()
        mMap!!.isMyLocationEnabled = true
        getLastLocation(mMap!!)
        startLocationUpdates()

    }

/*location management*/

    private fun getLastLocation(googleMap: GoogleMap) {
        val locationClient = LocationServices.getFusedLocationProviderClient(activity!!)
        mMap = googleMap;
        try {
            locationClient.lastLocation
                .addOnSuccessListener { location -> // GPS location can be null if GPS is switched off
                    if (location != null) {
                        if (mMap != null) {
                            // updateLoc(location)
                            mMap!!.moveCamera(
                                CameraUpdateFactory.newLatLngZoom(
                                    LatLng(
                                        location.latitude,
                                        location.longitude
                                    ), 15f
                                )
                            )
                        }
                    }
                }
                .addOnFailureListener { e ->
                    e.printStackTrace()
                }
        } catch (e: SecurityException) {
            e.printStackTrace()
        }
    }


    private val mLocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            // do work here
            locationResult.lastLocation
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: android.location.Location) {
        lastLocation = location
        if (lastLocation != null) {
            // updateLoc(location)  //CRASH
            // viewModel.updateLocation(pocklesLocation) //CRASH
            mMap!!.moveCamera(
                CameraUpdateFactory.newLatLngZoom(
                    LatLng(
                        location.latitude,
                        location.longitude
                    ), 18f
                )
            )

        }

    }

    protected fun startLocationUpdates() {

        // Create the location request to start receiving updates
        mLocationRequest = LocationRequest.create()
        mLocationRequest!!.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest!!.setInterval(INTERVAL)
        mLocationRequest!!.setFastestInterval(FASTEST_INTERVAL)

        // Create LocationSettingsRequest object using location request
        val builder = LocationSettingsRequest.Builder()
        builder.addLocationRequest(mLocationRequest!!)
        val locationSettingsRequest = builder.build()

        val settingsClient = LocationServices.getSettingsClient(activity!!)
        settingsClient.checkLocationSettings(locationSettingsRequest)

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(activity!!)
        // new Google API SDK v11 uses getFusedLocationProviderClient(this)

        mFusedLocationProviderClient!!.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    /* I need android.location for later but also need our own type of location*/
    /*THIS CRASHES ATM*/
    private fun updateLoc(location: android.location.Location) {
        lastLocation = location
        pocklesLocation.latitude = lastLocation.latitude.toFloat()
        pocklesLocation.longitude = lastLocation.longitude.toFloat()
    }

    /*You have the gps off function*//*
    private fun buildAlertMessageNoGps() {
        val builder = AlertDialog.Builder(context.let {
            builder.setMessage("Your GPS seems to be disabled, do you want to enable it?")
                .setCancelable(false)
                .setPositiveButton("Yes") { dialog, id ->
                    startActivityForResult(
                        Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                        , 11
                    )
                }
                .setNegativeButton("No") { dialog, id ->
                    dialog.cancel()
                    finish()
                }
        })
        val alert: AlertDialog = builder.create()
        alert.show()
    }*/
    /* Permission management **/
/* THIS NEEDS A REWORK IN THE FOLLOWING SPRINTS */
    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    private fun setUpMap() {

        if (context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_FINE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED &&
            context?.let {
                ActivityCompat.checkSelfPermission(
                    it,
                    android.Manifest.permission.ACCESS_COARSE_LOCATION
                )
            } != PackageManager.PERMISSION_GRANTED) {
            this.activity?.let {
                ActivityCompat.requestPermissions(
                    it,
                    arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }; return
        }
    }
}
