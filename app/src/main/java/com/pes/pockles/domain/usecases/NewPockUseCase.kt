package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.implementation.PockRepositoryImpl
import com.pes.pockles.domain.repositories.PockRepository
import com.pes.pockles.model.Pock

class NewPockUseCase(private val pockRepository: PockRepository = PockRepositoryImpl()) {

    fun execute(pock: Pock): LiveData<Resource<Pock>> {
        return pockRepository.newPock(pock)
    }
}