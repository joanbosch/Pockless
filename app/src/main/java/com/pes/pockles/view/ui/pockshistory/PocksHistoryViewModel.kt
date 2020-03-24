package com.pes.pockles.view.ui.pockshistory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.pes.pockles.R
import com.pes.pockles.data.Resource
import com.pes.pockles.model.Pock



class PocksHistoryViewModel : ViewModel() {
    //private val _errorHandler = MutableLiveData<Boolean>()
    private val _pocksHistory = MutableLiveData<Array<Pock>>()


    /*val errorHandlerCallback: LiveData<Boolean>
        get() = Transformations.map(_errorHandler) { value: Boolean ->
            value
        }*/

    val  pocksHistory : LiveData<Array<Pock>?>
        get() = Transformations.map(_pocksHistory) { value: Array<Pock>? ->
            value
        }

    fun getPocksHistory(){
        //This function must call to PocksHistoryUseCase
    }
}