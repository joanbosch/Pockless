package com.pes.pockles.view.ui.pockshistory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.pes.pockles.R
import com.pes.pockles.model.Pock



class PocksHistoryViewModel : ViewModel() {
    private val _errorHandler = MutableLiveData<Boolean>()
    private val _pocksHistory = MutableLiveData<Array<Pock>>()

    fun pocksHistory(){
        //This function must call to PocksHistoryUseCase
    }
}