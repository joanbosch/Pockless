package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.implementation.MapRepositoryImpl
import com.pes.pockles.domain.repositories.MapRepository
import com.pes.pockles.model.Location
import com.pes.pockles.model.Pock

class GetNearestPocksUseCase(private val mapRepository: MapRepository = MapRepositoryImpl()) {

    fun execute(value: Location): LiveData<Resource<List<Pock>>> {
        return mapRepository.getPocks(value)
    }
}