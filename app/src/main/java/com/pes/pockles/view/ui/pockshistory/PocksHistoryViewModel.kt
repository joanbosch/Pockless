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
    val pocksHistory: LiveData<Resource<List<Pock>>>
        get() = useCase.execute()

    private val useCase: PocksHistoryUseCase by lazy {
        PocksHistoryUseCase()
    }
}

