package com.pes.pockles.view.ui.map

import androidx.lifecycle.*
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.GetNearestPocksUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock
import com.pes.pockles.util.AbsentLiveData
import com.pes.pockles.util.extensions.forceRefresh

class MapViewModel : ViewModel() {

    private val useCase: GetNearestPocksUseCase by lazy {
        GetNearestPocksUseCase()
    }

    private val _currentLocation = MutableLiveData<Location?>()

    val categories = arrayOf(
        "Anuncios",
        "Compra&Venta",
        "Deportes",
        "Entretenimiento",
        "Mascotas",
        "Salud",
        "Tecnología",
        "Tursimo",
        "+18",
        "Varios"
    )
    val checkedItems = booleanArrayOf(true, true, true, true, true, true, true, true, true, true)

    init {
        _currentLocation.value = Location(0.0, 0.0)
    }

    private val _pocks: LiveData<Resource<List<Pock>>?>
        get() = Transformations.switchMap(_currentLocation) { value: Location? ->
            if (value != null) useCase.execute(value) else AbsentLiveData.create()
        }

    private val internalPocks: MediatorLiveData<Resource<List<Pock>>?> = MediatorLiveData()

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
}
