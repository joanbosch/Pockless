package com.pes.pockles.view.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.GetNearestPocksUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock
import com.pes.pockles.util.AbsentLiveData

class MapViewModel : ViewModel() {

    private val useCase: GetNearestPocksUseCase by lazy {
        GetNearestPocksUseCase()
    }

    private val _currentLocation = MutableLiveData<Location?>()

    init {
        _currentLocation.value = Location(0.0, 0.0)
    }

    val pocks: LiveData<Resource<List<Pock>>?>
        get() = Transformations.switchMap(_currentLocation) { value: Location? ->
            if (value != null) useCase.execute(value) else AbsentLiveData.create()
        }

    fun updateLocation(loc: android.location.Location) {
        _currentLocation.value = Location(longitude = loc.longitude, latitude = loc.latitude)
    }
}
