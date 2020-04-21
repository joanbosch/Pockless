package com.pes.pockles.data.repository

import androidx.lifecycle.LiveData
import com.google.android.gms.maps.model.LatLng
import com.pes.pockles.data.Resource
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.data.database.AppDatabase
import com.pes.pockles.model.Location
import com.pes.pockles.model.NewPock
import com.pes.pockles.model.Pock
import io.reactivex.functions.Function
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PockRepository @Inject constructor(
    apiService: ApiService
) : BaseRepository(apiService) {

    fun newPock(pock: NewPock): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.newPock(pock) })
    }

    fun getViewPock(id: String): LiveData<Resource<Pock>> {
        return callApi(Function { apiService -> apiService.viewPock(id) })
    }

    fun getPocks(loc: Location): LiveData<Resource<List<Pock>>> {
        return callApi(Function { apiService ->
            apiService.getNearPocks(
                loc.latitude,
                loc.longitude
            )
        })

    }

    fun getPocksLocation(): LiveData<Resource<List<LatLng>>> {
        return callApi(Function { apiService -> apiService.pocksLocation() })
    }
}