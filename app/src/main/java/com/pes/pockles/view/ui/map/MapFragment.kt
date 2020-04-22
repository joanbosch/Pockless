package com.pes.pockles.view.ui.map


import android.content.Intent
import android.os.Bundle
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.afollestad.assent.Permission
import com.afollestad.assent.runWithPermissions
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.FragmentMapBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.util.LocationUtils.Companion.getLastLocation
import com.pes.pockles.view.ui.base.BaseFragment
import com.pes.pockles.view.ui.viewpock.ViewPockActivity
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.ln


/**
 * A [Fragment] subclass for map view.
 */
// TODO: Add a fucking loader -> DANI
open class MapFragment : BaseFragment<FragmentMapBinding>(), OnMapReadyCallback {

    override fun getLayout(): Int {
        return R.layout.fragment_map
    }

    companion object {
        const val INTERVAL: Long = 60 * 1000 //interval for updates the loc
        const val FASTEST_INTERVAL: Long = 10 * 1000 //this is when it need higher precision
        const val RADIUS = 500 //In m
        const val MIN_DISPLACEMENT = 10f //In m
    }

    private val viewModel: MapViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
    }
    private var googleMap: GoogleMap? = null
    private lateinit var linearLayoutManager: LinearLayoutManager
    private var recyclerView: RecyclerView? = null
    private lateinit var pockList: MutableList<Pock>
    private lateinit var bottomSheetFragment: BottomSheetsPocks

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)


        binding.mapViewModel = viewModel

        val mapFragment = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        viewModel.categories = resources.getStringArray(R.array.categories)
        binding.outlinedButton.setOnClickListener {
            showFilterDialog()
        }
        pockList = mutableListOf<Pock>()
        return binding.root
    }

    private fun showFilterDialog() {

        val dialog: AlertDialog = AlertDialog.Builder(context!!)
            .setTitle(resources.getString(R.string.filter_dialog_text))
            .setMultiChoiceItems(
                viewModel.categories,
                viewModel.checkedItems
            ) { _, which, isChecked ->
                viewModel.setFilterItem(which, isChecked)
            }
            .setPositiveButton(resources.getString(R.string.close_filter_button)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()

        dialog.show()
    }


    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        runWithPermissions(Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION) {
            googleMap.setMapStyle(
                MapStyleOptions.loadRawResourceStyle(
                    context, R.raw.map_style
                )
            );
            googleMap.setMaxZoomPreference(19.0f);
            val uiSettings = googleMap.uiSettings
            uiSettings.isScrollGesturesEnabled = false
            setupMap();

            startLocationUpdates()
            bottomSheetFragment = BottomSheetsPocks()
            viewModel.getPocks().observe(
                this,
                Observer { value: Resource<List<Pock>>? ->
                    value?.let {
                        when (value) {
                            is Resource.Success<*> -> handleSuccess(value as Resource.Success<List<Pock>>)
                            is Resource.Error -> handleError()
                        }
                    }
                })
        }
        createBottomSheet()
    }

    private fun setupMap() {
        googleMap!!.isMyLocationEnabled = true
        googleMap!!.cameraPosition

        getLastLocation(activity!!, { location ->
            val latLng = LatLng(location.latitude, location.longitude)
            val center: CameraPosition = CameraPosition.Builder()
                .target(latLng)
                .zoom(getZoomForMetersWide(latLng))
                .build();
            googleMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(center))
        }, {
            Timber.d(it)
        })

        googleMap!!.setOnMarkerClickListener { marker ->
            val intent = Intent(activity, ViewPockActivity::class.java)
            intent.putExtra("markerId", marker.tag as String)
            startActivity(intent)
            true
        }
    }

    private fun getZoomForMetersWide(
        latLngPoint: LatLng
    ): Float {
        // calculate width screen
        val displayMetrics = DisplayMetrics()
        activity!!.windowManager.defaultDisplay.getMetrics(displayMetrics)
        val mapViewWidth: Int = displayMetrics.widthPixels

        // get zoom for meters
        val metrics: DisplayMetrics = context!!.resources.displayMetrics
        val mapWidth: Float = mapViewWidth / metrics.density
        val latitudinalAdjustment: Double = cos(Math.PI * latLngPoint.latitude / 180.0)
        val arg: Double = 40075004 * mapWidth * latitudinalAdjustment / (RADIUS * 256.0)
        return (ln(arg) / ln(2.0)).toFloat()
    }

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            onLocationChanged(locationResult.lastLocation)
        }
    }

    fun onLocationChanged(location: android.location.Location?) {
        location?.let {
            viewModel.updateLocation(it)
        }
    }

    private fun startLocationUpdates() {
        val locationRequest: LocationRequest = LocationRequest.create()
        locationRequest.priority = LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY
        locationRequest.interval = INTERVAL
        locationRequest.fastestInterval = FASTEST_INTERVAL
        locationRequest.smallestDisplacement = MIN_DISPLACEMENT

        LocationServices.getFusedLocationProviderClient(activity!!).requestLocationUpdates(
            locationRequest, locationCallback, Looper.myLooper()
        )
    }

    /*Link to know how to customize markers
     *https://developers.google.com/maps/documentation/android-sdk/marker?hl=es*/
    private fun handleSuccess(list: Resource.Success<List<Pock>>) {
        googleMap!!.clear()
        list.data.let {
            it.forEach { pock ->
                pockList.add(pock)
                val latLng = LatLng(
                    pock.location.latitude,
                    pock.location.longitude
                )
                val marker: Marker = googleMap!!.addMarker(MarkerOptions().position(latLng))
                marker.tag = pock.id
            }
        }
        sendData(bottomSheetFragment)
    }

    private fun handleError() {
        val text = getString(R.string.failed_loc)
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    //BOTTOM SHEET
    private fun createBottomSheet() {
        /*
        val bottomSheetBehavior = BottomSheetBehavior.from(pock_list)

        floatingActionButton.setOnClickListener {
            if (bottomSheetBehavior.state == BottomSheetBehavior.STATE_COLLAPSED) {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_EXPANDED
            } else {
                bottomSheetBehavior.state = BottomSheetBehavior.STATE_COLLAPSED
            }
        }
        //RecyclerView of pocks
        linearLayoutManager = LinearLayoutManager(context)
        recyclerView = view?.findViewById(R.id.nearPockList);
        recyclerView?.layoutManager = linearLayoutManager
        adapter = PockListAdapter(pockList)
        recyclerView?.adapter = adapter
*/
        binding.showSheetBtn.setOnClickListener {
           // bottomSheetFragment = BottomSheetsPocks()
            bottomSheetFragment.show(requireActivity().supportFragmentManager, "BottomSheetsPocks")
        }
    }

    private fun sendData(bs: BottomSheetsPocks) {
        bs.setData(pockList)

    }
}



