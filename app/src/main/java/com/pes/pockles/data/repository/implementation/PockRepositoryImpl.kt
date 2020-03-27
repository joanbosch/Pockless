package com.pes.pockles.data.repository.implementation

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.BaseRepository
import com.pes.pockles.domain.repositories.PockRepository
import com.pes.pockles.model.Pock
import io.reactivex.functions.Function

class PockRepositoryImpl : BaseRepository(), PockRepository {

    override fun newPock(pock: Pock): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.newPock(pock) })
    }
}