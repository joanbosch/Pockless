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
        private val currentLocation = MutableLiveData<Location?>()
        val networkCallback: LiveData<Resource<Pock>?>
            get() = Transformations.switchMap(currentLocation) { value: pock? ->
                if (value != null) useCase.execute(value) else AbsentLiveData.create()
            }

        val pockTitle = MutableLiveData<String>()
        val pockContent = MutableLiveData<String>()
        val pockCategory = MutableLiveData<String>()
        val chatEnabled = MutableLiveData<Boolean>()

        init {
            chatEnabled.value = false
        }

        fun insertPock() {
            _pockToInsert.value = Pock(
                message = pockContent.value!!,
                category = pockCategory.value!!,
                chatAccess = chatEnabled.value!!,
                location = Location(0f, 0f) // obtain current location
            )
        }

    }
}