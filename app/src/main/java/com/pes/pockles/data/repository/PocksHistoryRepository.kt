package com.pes.pockles.data.repository

import androidx.lifecycle.LiveData
import com.pes.pockles.data.Resource
import com.pes.pockles.data.api.ApiService
import com.pes.pockles.data.database.AppDatabase
import com.pes.pockles.model.Pock
import io.reactivex.functions.Function
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PocksHistoryRepository @Inject constructor(
    var database: AppDatabase,
    apiService: ApiService
) : BaseRepository(apiService) {

    fun getPocksHistory(): LiveData<Resource<List<Pock>>> {
        return callApi(Function { apiService -> apiService.pocksHistory() })
    }
}