package com.pes.pockles.view.ui.newpock

import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.newpock.NewPockUseCase
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock

class NewPockViewModel : ViewModel() {

    val pockTitle = MutableLiveData<String>()
    val pockContent = MutableLiveData<String>()
    val pockCategory = MutableLiveData<String>()
    val chatEnabled = MutableLiveData<Boolean>()

    val useCase: NewPockUseCase by lazy {
        NewPockUseCase()
    }

    init {
        chatEnabled.value = false
    }

    fun insertPock() {
        val mediatorLiveData: MediatorLiveData<Resource<Pock>> = MediatorLiveData()
        mediatorLiveData.addSource(
            useCase.execute(
                Pock(
                    message = pockContent.value!!,
                    category = pockCategory.value!!,
                    chatAccess = chatEnabled.value!!,
                    location = Location(0f, 0f)
                )
            )
        ) { data : Resource<Pock>? ->
            data?.let { checkResult(data) }
        }

    }

    private fun checkResult(data: Resource<Pock>) {
        val a = 0
    }
}