package com.pes.pockles.view.ui.editpock

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.EditPockUseCase
import com.pes.pockles.model.EditedPock
import com.pes.pockles.model.Pock
import com.pes.pockles.util.AbsentLiveData

class EditPockViewModel : ViewModel() {
    private val _errorHandler = MutableLiveData<Boolean>()
    private val _chatEnabled = MutableLiveData<Boolean>()
    private val _pockToUpdate = MutableLiveData<EditedPock?>()
    val pockContent = MutableLiveData<String>()
    val pockCategory = MutableLiveData<String>()


    private val useCase: EditPockUseCase by lazy {
        EditPockUseCase()
    }

    val networkCallback: LiveData<Resource<Pock>?>
        get() = Transformations.switchMap(_pockToUpdate) { value: EditedPock? ->
            if (value != null) useCase.execute("id_del_pock_a_editar", value) else AbsentLiveData.create()
        }

    val errorHandlerCallback: LiveData<Boolean>
        get() = _errorHandler

    init {
        _chatEnabled.value = false
    }

    fun updatePock() {
        val category: String = if (pockCategory.value == null)
            "General"
        else pockCategory.value.toString()

        if (pockContent.value == null)
            _errorHandler.value = true
        else {
            _pockToUpdate.value = EditedPock(
                message = pockContent.value!!,
                category = category,
                chatAccess = _chatEnabled.value!!
            )
        }
    }

}