package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.implementation.PocksHistoryRepositoryImpl
import com.pes.pockles.domain.repositories.PocksHistoryRepository
import com.pes.pockles.model.Pock

class PocksHistoryUseCase(private val repository: PocksHistoryRepository = PocksHistoryRepositoryImpl()) {

    fun execute(): LiveData<Resource<List<Pock>>> {
        return repository.getPocksHistory()
    }

}