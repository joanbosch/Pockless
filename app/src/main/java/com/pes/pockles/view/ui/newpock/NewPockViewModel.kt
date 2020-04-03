package com.pes.pockles.view.ui.newpock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.NewPockUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.NewPock
import com.pes.pockles.model.Pock
import com.pes.pockles.util.AbsentLiveData

class NewPockViewModel : ViewModel() {
    private val _errorHandler = MutableLiveData<Boolean>()
    private val _chatEnabled = MutableLiveData<Boolean>()
    private val _pockToInsert = MutableLiveData<NewPock?>()
    val pockContent = MutableLiveData<String>()
    val pockCategory = MutableLiveData<String>()


    private val useCase: NewPockUseCase by lazy {
        NewPockUseCase()
    }

    val networkCallback: LiveData<Resource<Pock>?>
        get() = Transformations.switchMap(_pockToInsert) { value: NewPock? ->
            if (value != null) useCase.execute(value) else AbsentLiveData.create()
        }

    val errorHandlerCallback: LiveData<Boolean>
        get() = _errorHandler

    init {
        _chatEnabled.value = false
    }

    fun insertPock(location: Location) {
        val category: String = if (pockCategory.value == null)
            "General"
        else pockCategory.value.toString()

        if (pockContent.value == null)
            _errorHandler.value = true
        else {
            _pockToInsert.value = NewPock(
                message = pockContent.value!!,
                category = category,
                chatAccess = _chatEnabled.value!!,
                location = location
            )
        }
    }

}