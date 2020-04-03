package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.implementation.PockRepositoryImpl
import com.pes.pockles.domain.repositories.PockRepository
import com.pes.pockles.model.Pock

class ViewPockUseCase(private val repository: PockRepository = PockRepositoryImpl()) {
    fun execute(id: String): LiveData<Resource<Pock>> {
        return repository.getViewPock(id)
    }
}