package com.pes.pockles.view.ui.pockshistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.PocksHistoryUseCase
import com.pes.pockles.model.Pock

class PocksHistoryViewModel : ViewModel() {

    val pocksHistory: LiveData<Resource<List<Pock>>>
        get() = _pocksHistory

    private val _pocksHistory = MediatorLiveData<Resource<List<Pock>>>()

    private val useCase: PocksHistoryUseCase by lazy {
        PocksHistoryUseCase()
    }

   // Executed when RecyclerView must be updated
    fun refreshInformation() {
        val source = useCase.execute()
        _pocksHistory.addSource(source){
            _pocksHistory.value = it
            if (it != Resource.Loading) _pocksHistory.removeSource(source)
        }
    }
}

