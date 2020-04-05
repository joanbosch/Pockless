package com.pes.pockles.data.repository.implementation

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.repository.BaseRepository
import com.pes.pockles.domain.repositories.PockRepository
import com.pes.pockles.model.EditedPock
import com.pes.pockles.model.Location
import com.pes.pockles.model.NewPock
import com.pes.pockles.model.Pock
import io.reactivex.functions.Function

class PockRepositoryImpl : BaseRepository(), PockRepository {

    override fun newPock(pock: NewPock): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.newPock(pock) })
    }

    override fun getViewPock(id: String): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.viewPock(id) })
    }

    override fun getPocks(loc: Location): LiveData<Resource<List<Pock>>> {
        return callApi(Function { apiService -> apiService.getNearPocks(loc.latitude, loc.longitude) })

    }

    override fun editPock(id:String, pock: EditedPock): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.editPock(id, pock) })
    }
}