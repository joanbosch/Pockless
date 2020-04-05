package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.implementation.PockRepositoryImpl
import com.pes.pockles.domain.repositories.PockRepository
import com.pes.pockles.model.EditedPock
import com.pes.pockles.model.Pock

class EditPockUseCase(private val pockRepository: PockRepository = PockRepositoryImpl()) {
    fun execute(id: String, pock: EditedPock): LiveData<Resource<Pock>> {
        return pockRepository.editPock(id, pock)
    }
}