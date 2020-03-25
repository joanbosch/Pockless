package com.pes.pockles.view.ui.map

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.GetPocksForMapUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock
import com.pes.pockles.util.AbsentLiveData

class MapViewModel : ViewModel() {

    private val useCase: GetPocksForMapUseCase by lazy {
        GetPocksForMapUseCase()
    }
    private var currentLocation = MutableLiveData<Location?>()
    private lateinit var pockList: List<Pock>
    fun updateLocation(loc: Location) {
        currentLocation.value = loc
    }
    /*TODO
    val networkCallback: LiveData<Resource<List<Pock>>>
        get() = Transformations.switchMap(currentLocation) { value: List<Pock>->
            if (value != null) useCase.execute(value) else AbsentLiveData.create()
        }*/


}
