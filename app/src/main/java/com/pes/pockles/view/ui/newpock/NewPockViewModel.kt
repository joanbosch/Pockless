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
    private val _errorHandler = MutableLiveData<Boolean>()
    private val _chatEnabled = MutableLiveData<Boolean>()
    private val _pockToInsert = MutableLiveData<Pock?>()
    val pockContent = MutableLiveData<String>()
    val pockCategory = MutableLiveData<String>()

    private val useCase: NewPockUseCase by lazy {
        NewPockUseCase()
    }

    val loading: LiveData<Boolean>
        get() = Transformations.map(networkCallback) { value: Resource<Pock>? ->
            value != null && value is Resource.Loading
        }

    val networkCallback: LiveData<Resource<Pock>?>
        get() = Transformations.switchMap(_pockToInsert) { value: Pock? ->
            if (value != null) useCase.execute(value) else AbsentLiveData.create()
        }

    val keyboardCallback: LiveData<Boolean>
        get() = Transformations.map(networkCallback) { value: Resource<Pock>? ->
            value != null && value is Resource.Loading
        }

    val errorHandlerCallback: LiveData<Boolean>
        get() = Transformations.map(_errorHandler) { value: Boolean ->
            value
        }

    init {
        _chatEnabled.value = false
    }

    fun insertPock() {
        val category: String = if (pockCategory.value == null)
            "General"
        else pockCategory.value.toString()

        if (pockContent.value == null)
            _errorHandler.value = true
        else {
            _pockToInsert.value = Pock(
                message = pockContent.value!!,
                category = category,
                chatAccess = _chatEnabled.value!!,
                location = Location(0.0, 0.0) // obtain current location
            )
        }
    }

}