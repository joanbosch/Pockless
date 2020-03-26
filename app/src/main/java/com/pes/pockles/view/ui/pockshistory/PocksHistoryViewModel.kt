package com.pes.pockles.view.ui.pockshistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.domain.usecases.PocksHistoryUseCase
import com.pes.pockles.model.Pock



class PocksHistoryViewModel : ViewModel() {
    //private val _errorHandler = MutableLiveData<Boolean>()
    private val _pocksHistory = MutableLiveData<List<Pock>>()
    private val useCase: PocksHistoryUseCase by lazy {
        PocksHistoryUseCase()
    }
    init {
        getPocksHistory()
    }

    /*val errorHandlerCallback: LiveData<Boolean>
        get() = Transformations.map(_errorHandler) { value: Boolean ->
            value
        }*/

    val  pocksHistory : LiveData<List<Pock>?>
        get() = Transformations.map(_pocksHistory) { value: List<Pock>? ->
            value
        }

    private fun getPocksHistory() {
        useCase.execute()
        //This function must call to PocksHistoryUseCase
    }
}