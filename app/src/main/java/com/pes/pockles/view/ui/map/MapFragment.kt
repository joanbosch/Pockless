package com.pes.pockles.view.ui.map


import android.content.Intent
import android.graphics.Bitmap
import android.graphics.Canvas
import android.os.Bundle
import android.os.Looper
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
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
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.maps.android.heatmaps.HeatmapTileProvider
import com.mikepenz.fastadapter.FastAdapter
import com.mikepenz.fastadapter.adapters.ItemAdapter
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.databinding.FragmentMapBinding
import com.pes.pockles.model.Pock
import com.pes.pockles.util.LocationUtils.Companion.getLastLocation
import com.pes.pockles.util.dp2px
import com.pes.pockles.view.ui.base.BaseFragment
import com.pes.pockles.view.ui.pockshistory.item.BindingPockItem
import com.pes.pockles.view.ui.viewpock.ViewPockActivity
import timber.log.Timber
import kotlin.math.cos
import kotlin.math.ln
import kotlin.math.roundToInt


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
        const val HM_ZOOM = 15 // Max Zoom Lever for HeatMap
    }


    private val viewModel: MapViewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(MapViewModel::class.java)
    }

    private var googleMap: GoogleMap? = null
    private var mProvider: HeatmapTileProvider? = null

    // Add Listener to change this variable when zoom is in x number
    private var heatMapEnabled: Boolean = true
    private lateinit var images: Map<String, BitmapDescriptor>

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
            loadImages()

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

            viewModel.getAllLatLngPocks().observe(
                this,
                Observer { value: Resource<List<LatLng>>? ->
                    value?.let {
                        when (value) {
                            is Resource.Success<*> -> handleSuccessHeatMap(value as Resource.Success<List<LatLng>>)
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

        googleMap!!.setOnCameraIdleListener {
            if (heatMapEnabled != (googleMap!!.cameraPosition.zoom < HM_ZOOM)) {
                heatMapEnabled = (googleMap!!.cameraPosition.zoom < HM_ZOOM)
                // If HeatMap Enabled (true) and zoomIN -> Put HeatMap
                if (heatMapEnabled) {
                    viewModel.getAllLatLngPocks()
                    googleMap!!.uiSettings.isScrollGesturesEnabled = true
                }
                //If HeatMap Disabled (false) and ZoomOut -> Put Markers
                else {
                    viewModel.getPocks()
                    googleMap!!.uiSettings.isScrollGesturesEnabled = false
                }
            }
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
        var cat:String
        list.data.let {
            it.forEach { pock ->

                val latLng = LatLng(
                    pock.location.latitude,
                    pock.location.longitude
                )
                cat = pock.category
                if (!heatMapEnabled) {
                    val marker: Marker = googleMap!!.addMarker(MarkerOptions().position(latLng))
                    marker.tag = pock.id
                    marker.setIcon(images[cat])
                }
            }

            val pockListBinding: List<BindingPockItem> = it.map { pock ->
                val binding =
                    BindingPockItem()
                binding.pock = pock
                binding
            }
            //Fill and set the items to the ItemAdapter
            itemAdapter.setNewList(pockListBinding)
        }
    }
    private fun handleSuccessHeatMap(pocksLocations: Resource.Success<List<LatLng>>) {
        googleMap!!.clear()
        pocksLocations.data.let {
            if (heatMapEnabled) {
                mProvider = HeatmapTileProvider.Builder().data(it).build()
                mProvider!!.setRadius(30)
                googleMap!!.addTileOverlay(TileOverlayOptions().tileProvider(mProvider))
            }
        }
    }

    private fun handleError() {
        val text = getString(R.string.failed_loc)
        val duration = Toast.LENGTH_SHORT

        val toast = Toast.makeText(context, text, duration)
        toast.show()
    }

    private val itemAdapter = ItemAdapter<BindingPockItem>()

    //BOTTOM SHEET
    private fun createBottomSheet() {
        val behaviour = BottomSheetBehavior.from(binding.bottomSheet)
        behaviour.peekHeight = dp2px(context!!, 120f).roundToInt()
        val fastAdapter = FastAdapter.with(itemAdapter)
        binding.nearPockList.apply {
            layoutManager = LinearLayoutManager(activity)
            adapter = fastAdapter
        }

        fastAdapter.onClickListener = { _, _, item, position ->
            val intent = Intent(activity, ViewPockActivity::class.java)
            intent.putExtra("markerId", item.pock?.id as String)
            startActivity(intent)
            true
        }
    }

    private fun loadImages() {
        val iconoTurismo = BitmapDescriptorFactory.fromResource(R.raw.icono_turismo)
        val iconoDeportes = BitmapDescriptorFactory.fromResource(R.raw.icono_deportes)
        val iconoEntre = BitmapDescriptorFactory.fromResource(R.raw.icono_entre)
        val iconoVarios = BitmapDescriptorFactory.fromResource(R.raw.icono_mail)
        val iconoTec = BitmapDescriptorFactory.fromResource(R.raw.icono_tecnologia)
        val icono18 = BitmapDescriptorFactory.fromResource(R.raw.icono_18)
        val iconoSalud = BitmapDescriptorFactory.fromResource(R.raw.icono_salud)
        val iconoAnuncio = BitmapDescriptorFactory.fromResource(R.raw.icono_anunci)
        val iconoGeneral = BitmapDescriptorFactory.fromResource(R.raw.icono_mail)
        val iconoMascotas = BitmapDescriptorFactory.fromResource(R.raw.icono_mail)
        images = mapOf("Turismo" to iconoTurismo, "Varios" to iconoVarios, "Salud" to iconoSalud,
            "Entretenimiento" to iconoEntre, "Tecnologia" to iconoTec, "+18" to icono18, "Compra y Venta" to iconoVarios,
        "Anuncios" to iconoAnuncio, "Deportes" to iconoDeportes, "General" to iconoGeneral, "Mascotas" to iconoMascotas) as Map<String, BitmapDescriptor>
    }

    //useless fun for now, it will become useful when the .svg are delivered
    private fun bitmapDescriptorFromVector(vectorResId: Int): BitmapDescriptor? {
        val height = dp2px(context!!, 100f).roundToInt()
        return ContextCompat.getDrawable(context!!, vectorResId)?.run {
            setBounds(0, 0, height, height)
            val bitmap =
                Bitmap.createBitmap(height, height, Bitmap.Config.ARGB_8888)
            draw(Canvas(bitmap))
            BitmapDescriptorFactory.fromBitmap(bitmap)
        }
    }
}
