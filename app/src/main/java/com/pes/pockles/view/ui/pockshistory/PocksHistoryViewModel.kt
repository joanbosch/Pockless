package com.pes.pockles.view.ui.pockshistory

import androidx.lifecycle.*
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.PocksHistoryUseCase
import com.pes.pockles.model.Pock



class PocksHistoryViewModel : ViewModel() {

    fun refreshInformation() {
        val aux = MediatorLiveData<Resource<List<Pock>>>()
        val source = useCase.execute()
        aux.addSource(source){
            _pocksHistory.value = it
            if (it != Resource.Loading) aux.removeSource(source)
        }
    }

    val pocksHistory: LiveData<Resource<List<Pock>>>
        get() = _pocksHistory

    private val _pocksHistory = MutableLiveData<Resource<List<Pock>>>()

    private val useCase: PocksHistoryUseCase by lazy {
        PocksHistoryUseCase()
    }

}

