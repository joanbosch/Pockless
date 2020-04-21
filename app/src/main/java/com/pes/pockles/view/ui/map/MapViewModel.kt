package com.pes.pockles.view.ui.map

import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.GetNearestPocksUseCase
import com.pes.pockles.domain.usecases.PocksLocationUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock
import com.pes.pockles.util.livedata.AbsentLiveData
import com.pes.pockles.util.extensions.forceRefresh
import javax.inject.Inject

class MapViewModel @Inject constructor(
    private var useCaseNearestPocks: GetNearestPocksUseCase,
    private var useCaseAllPocks: PocksLocationUseCase
) : ViewModel() {

    lateinit var categories :Array<String>
    private val _currentLocation = MutableLiveData<Location?>()
    val checkedItems = booleanArrayOf(true, true, true, true, true, true, true, true, true, true)

    init {
        _currentLocation.value = Location(0.0, 0.0)
    }

    private val _pocks: LiveData<Resource<List<Pock>>?>
        get() = Transformations.switchMap(_currentLocation) { value: Location? ->
            if (value != null) useCaseNearestPocks.execute(value) else AbsentLiveData.create()
        }

    private val _allPocksLocations: LiveData<Resource<List<LatLng>>>
        get() = useCaseAllPocks.execute()


    private val internalPocks: MediatorLiveData<Resource<List<Pock>>?> = MediatorLiveData()
    private val latLngAllPocks: MediatorLiveData<Resource<List<LatLng>>?> = MediatorLiveData()

    fun getPocks(): LiveData<Resource<List<Pock>>?> {
        internalPocks.addSource(_pocks) { value ->
            internalPocks.value = value
        }

        return Transformations.map(internalPocks) { value: Resource<List<Pock>>? ->
            if (value is Resource.Success<List<Pock>>) {
                Resource.Success(value.data.filter {
                    if (categories.contains(it.category)) {
                        checkedItems[categories.indexOf(it.category)]
                    } else true
                })
            } else value
        }
    }

    fun updateLocation(loc: android.location.Location) {
        _currentLocation.value = Location(longitude = loc.longitude, latitude = loc.latitude)
    }

    fun setFilterItem(position: Int, status: Boolean) {
        checkedItems[position] = status
        internalPocks.forceRefresh()
    }

    fun getAllLatLngPocks(): LiveData<Resource<List<LatLng>>?> {
        latLngAllPocks.addSource(_allPocksLocations) { value ->
            latLngAllPocks.value = value
        }
        return latLngAllPocks
    }
}
