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
    private val currentLocation = MutableLiveData<Location?>()
    fun updateLocation(loc: Location) {
        currentLocation.value = loc
    }


    val networkCallback: LiveData<Resource<List<Pock>>>
        get() = Transformations.switchMap(currentLocation) { value: Location? ->
            if (value != null) useCase.execute(value) else AbsentLiveData.create() /*TODO*/
        }

}
