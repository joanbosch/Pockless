package com.pes.pockles.domain.usecases

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.implementation.ViewPockRepositoryImpl
import com.pes.pockles.domain.repositories.ViewPockRepository
import com.pes.pockles.model.Pock

class ViewPockUseCase (private val viewpockRepository: ViewPockRepository = ViewPockRepositoryImpl()){
    fun execute(id: String): LiveData<Resource<Pock>> {
        return viewpockRepository.getViewPock(id)
    }
}