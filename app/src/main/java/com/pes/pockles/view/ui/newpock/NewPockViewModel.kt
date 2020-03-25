package com.pes.pockles.view.ui.newpock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.NewPockUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock
import com.pes.pockles.util.AbsentLiveData

class NewPockViewModel : ViewModel() {

    private val useCase: NewPockUseCase by lazy {
        NewPockUseCase()
    }

    private val _pockToInsert = MutableLiveData<Pock?>()
    val networkCallback: LiveData<Resource<Pock>>?
        get() = Transformations.switchMap(_pockToInsert) { value: Pock? ->
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