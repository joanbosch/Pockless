package com.pes.pockles.domain.repositories

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.model.Pock

interface PocksHistoryRepository {
    fun getPocksHistory(): LiveData<Resource<List<Pock>>>
}