package com.pes.pockles.data.repository.implementation

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.BaseRepository
import com.pes.pockles.domain.repositories.PocksHistoryRepository
import com.pes.pockles.model.Pock
import io.reactivex.functions.Function

class PocksHistoryRepositoryImpl : BaseRepository(), PocksHistoryRepository {

    override fun getPocksHistory(): LiveData<Resource<List<Pock>>> {
        return callApi(Function { apiService -> apiService.pocksHistory() })
    }
}