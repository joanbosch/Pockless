package com.pes.pockles.data.repository.implementation

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.BaseRepository
import com.pes.pockles.domain.repositories.ViewPockRepository
import com.pes.pockles.model.Pock
import io.reactivex.functions.Function

class ViewPockRepositoryImpl : BaseRepository(), ViewPockRepository  {

    override fun getViewPock(id: String): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.viewPock(id) })
    }
}